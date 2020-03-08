import select
import threading

from Lab1.homework.ClientOnServerSide import ClientOnServerSide
from Lab1.homework.PythonTcpClient import MySocket


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5

    def __init__(self):
        super().__init__()

        self.clients = dict()  # dict of ClientsOnTheServerSide
        self.sock.bind((self.SERVER_IP, self.PORT))
        self.sock.listen(ServerSocket.MAX_CLIENTS_COUNT)
        self.e = select.epoll()  # edge polling object
        self.run()

    def accept_connection(self):
        return self.sock.accept()

    def register_client(self, client_socket, address):
        # client's nickname is always the first message. Client won't connect if the nickname is unknown,
        # so we won't have to wait unless there are any network problems
        nickname = self.receive_message(client_socket)
        print("New client: " + nickname + " " + str(address[0]) + ":" + str(address[1]) + "!")

        fd = client_socket.fileno()
        # register client socket for reading
        self.e.register(fd, select.EPOLLIN)

        self.clients[fd] = ClientOnServerSide(client_socket, address, nickname)

    def send_msg_to_all_clients_excluding_sender(self, fd):
        client = self.clients[fd]
        msg = self.receive_message(client.sock)

        msg_to_send = ServerSocket.create_msg_from(client.nickname, msg)
        print("msg_to_send: " + msg_to_send)

        recipients = [self.clients[k] for k in self.clients.keys() if k != fd]

        print("recipients: " + str([r.nickname for r in recipients]))

        for recip in recipients:
            try:
                recip.sock.send(bytes(msg_to_send, 'utf8'))
                print("Sent message: \"" + msg_to_send + "\" to " + recip.nickname)
            except BrokenPipeError:
                print("Broken Pipe. Client must have exited")

    @staticmethod
    def create_msg_from(nickname, msg):
        return nickname + ": " + msg

    def close_socket(self):
        for client in self.clients:
            client.send_message('SERVER: Closing connection')
            client.close()
        self.sock.close()
        print("Server exiting")

    def run(self):
        connection_worker = threading.Thread(target=self.accept_connections)
        poll_worker = threading.Thread(target=self.poll)
        connection_worker.start()
        poll_worker.start()

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
