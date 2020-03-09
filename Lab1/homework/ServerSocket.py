import select
import threading

from Lab1.homework.ClientOnServerSide import ClientOnServerSide
from Lab1.homework.Client import MySocket


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5

    def __init__(self):
        super().__init__()

        self.tcp_clients = dict()  # dict of ClientsOnTheServerSide
        self.udp_clients = dict()
        self.tcp_socket.bind((self.SERVER_IP, self.PORT))
        self.udp_socket.bind(self.tcp_socket.getsockname())

        self.tcp_socket.listen(ServerSocket.MAX_CLIENTS_COUNT)
        self.e = select.epoll()  # edge polling object
        self.run()

    def accept_connection(self):
        return self.tcp_socket.accept()

    def register_client(self, client_socket, address):
        # client's nickname is always the first message. Client won't connect if the nickname is unknown,
        # so we won't have to wait unless there are any network problems
        nickname = self.receive_message_via_tcp(client_socket)
        print("New client: " + nickname + " " + str(address[0]) + ":" + str(address[1]) + "!")

        fd = client_socket.fileno()
        # register client socket for reading
        self.e.register(fd, select.EPOLLIN)

        self.tcp_clients[fd] = ClientOnServerSide(client_socket, address, nickname)
        self.udp_clients[address] = nickname

    def send_msg_to_all_clients_excluding_sender(self, fd):
        client = self.tcp_clients[fd]
        msg = self.receive_message_via_tcp(client.tcp_socket)

        msg_to_send = ServerSocket.create_msg_from(client.nickname, msg)
        print("msg_to_send: " + msg_to_send)

        recipients = [self.tcp_clients[k] for k in self.tcp_clients.keys() if k != fd]

        print("recipients: " + str([r.nickname for r in recipients]))

        try:
            for recip in recipients:
                recip.tcp_socket.send(bytes(msg_to_send, 'utf8'))
                print("Sent message: \"" + msg_to_send + "\" to " + recip.nickname)
        except BrokenPipeError:
            print("Broken Pipe. Client must have exited")
            del self.tcp_clients[fd]
            self.e.unregister(fd)

            for cl_k in self.udp_clients.keys():
                if self.udp_clients[cl_k] == client.nickname:
                    del self.udp_clients[cl_k]
                    break
            raise BrokenPipeError


    @staticmethod
    def create_msg_from(nickname, msg):
        return nickname + ": " + msg

    def close_socket(self):
        for client in self.tcp_clients:
            client.send_message_via_tcp('SERVER: Closing connection')
            client.close()
        self.tcp_socket.close()
        print("Server exiting")

    def run(self):
        connection_worker = threading.Thread(target=self.accept_connections)
        poll_worker = threading.Thread(target=self.poll)
        udp_worker = threading.Thread(target=self.udp_worker)
        connection_worker.start()
        poll_worker.start()
        udp_worker.start()

    def accept_connections(self):
        while True:
            client_tcp_socket, address = self.accept_connection()
            self.register_client(client_tcp_socket, address)

    def poll(self):
        try:
            while True:
                events = self.e.poll(self.EPOLL_TIMEOUT)
                for fd, event_type in events:
                    if event_type & select.EPOLLIN:
                        sender = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                  args=(fd,))
                        sender.start()
        except KeyboardInterrupt:
            print('Exiting')
        except BrokenPipeError:
            print("Client left the chat")

    def udp_worker(self):
        while True:
            msg, address = self.receive_message_via_udp()
            print("address: " + str(address))

            if address not in self.udp_clients.keys():  # socket ports from whose msgs are coming are different for
                # tcp and udp on my computer and I don't know why - to check it out: c = self.udp_clients[address]
                nickname = msg
                self.udp_clients[address] = nickname
                msg, address = self.receive_message_via_udp()

            msg_to_send = ServerSocket.create_msg_from(self.udp_clients[address], msg)
            print("msg_to_send: " + msg_to_send)

            addresses = [a for a in self.udp_clients.keys() if a != address]  # + [a for a in self.tcp_clients.keys() if
            # a != address]
            for address in addresses:
                print("address: " + str(address))

                print("Sent message: \"" + msg_to_send + "\" to " + str(address))
                self.send_message_via_udp(msg_to_send, address)

            # BOTH CLIENTS MUST HAVE INITIATED UDP COMMUNICATION!!!!!

            # /home/monika/PycharmProjects/DistibutedSystems/venv/bin/python /home/monika/PycharmProjects/DistibutedSystems/Lab1/homework/Server.py
            # New client: cd  127.0.0.1:40506!
            # msg_to_send: cd : mme
            # recipients: []
            # address: ('127.0.0.1', 51664)
            # msg_to_send: cd : fff
            # address: ('127.0.0.1', 40506)
            # Sent message: "cd : fff" to ('127.0.0.1', 40506)
            # address: 6
            # Sent message: "cd : fff" to 6
            # Exception in thread Thread-3:
            # Traceback (most recent call last):
            #   File "/usr/lib64/python3.7/threading.py", line 926, in _bootstrap_inner
            #     self.run()
            #   File "/usr/lib64/python3.7/threading.py", line 870, in run
            #     self._target(*self._args, **self._kwargs)
            #   File "/home/monika/PycharmProjects/DistibutedSystems/Lab1/homework/ServerSocket.py", line 114, in udp_worker
            #     self.send_message_via_udp(msg_to_send, address)
            #   File "/home/monika/PycharmProjects/DistibutedSystems/Lab1/homework/Client.py", line 78, in send_message_via_udp
            #     self.udp_socket.sendto(bytes(message, 'utf8'), addr)
            # TypeError: getsockaddrarg: AF_INET address must be tuple, not int
