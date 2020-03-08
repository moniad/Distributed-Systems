import select
import threading

from Lab1.homework.ClientOnServerSide import ClientOnServerSide
from Lab1.homework.PythonTcpClient import MySocket, Channel


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5

    def __init__(self):
        super().__init__()

        self.clients = dict()  # dict of ClientsOnTheServerSide, identifying clients by nickname (key in dict)
        self.tcp_sock.bind((self.SERVER_IP, self.TCP_PORT))
        self.tcp_sock.listen(ServerSocket.MAX_CLIENTS_COUNT)

        self.e = select.epoll()  # edge polling object

        self.udp_sock.bind((self.SERVER_IP, self.UDP_PORT))
        self.e.register(self.udp_sock.fileno(), select.EPOLLIN)

        self.run()

    def accept_connection(self):
        return self.tcp_sock.accept()

    def register_tcp_client(self, client_socket, address):
        # client's nickname is always the first message. Client won't connect if the nickname is unknown,
        # so we won't have to wait unless there are any network problems
        nickname = self.receive_message_via_tcp(client_socket)
        print("New client: " + nickname + " " + str(address[0]) + ":" + str(address[1]) + "!")

        fd = client_socket.fileno()
        # register client socket for reading
        self.e.register(fd, select.EPOLLIN)

        self.clients[nickname] = ClientOnServerSide(client_socket, fd)

    def register_udp_or_multicast_client(self, fd):
        nickname, addr = self.receive_message_via_udp()
        client = self.clients[nickname]

        if addr == self.MULTICAST_IP:
            new_client = ClientOnServerSide(client.tcp_sock, client.tcp_fd, multicast_address=addr, multicast_fd=fd)

            del self.clients[nickname]
            self.clients[nickname] = new_client

        elif addr == self.SERVER_IP:  # UDP
            new_client = ClientOnServerSide(client.tcp_sock, client.tcp_fd, addr, fd)

            del self.clients[nickname]
            self.clients[nickname] = new_client
            print("ON REGISTER ---- nickname: " + str(nickname) + ", addr : " + str(self.clients[nickname].udp_address))
        else:
            print("Unknown sevices")

    def send_msg_to_all_clients_excluding_sender(self, nickname, channel):
        client = self.clients[nickname]
        print('client data:  ' + str(client))
        if channel == Channel.TCP:
            msg = self.receive_message_via_tcp(client.tcp_sock)
        elif channel == Channel.UDP:
            msg, addr = self.receive_message_via_udp()
        else:  # multicast
            msg, addr = 'DEFAULT MULTICAST MSG!!! FIXME', 'addr'

        msg_to_send = ServerSocket.create_msg_from(nickname, msg)
        print("msg_to_send: " + msg_to_send)

        recipients = ServerSocket.get_recipients(self, nickname)

        for recip in recipients:
            try:
                if channel == Channel.TCP:
                    recip.send_message_via_tcp(msg_to_send)
                elif channel == Channel.UDP:
                    print("recipeint address: " + str(recip.udp_address) + " vs address: " + str(addr))
                    self.send_message_via_udp(msg_to_send, addr)  # recip.udp_address)
                else:  # multicast
                    # todo!
                    # self.send_message_via_multicast(msg_to_send, recip.multicast_address)
                    pass

                # print("Sent message: \"" + msg_to_send + "\" to " + recip.nickname)
            except BrokenPipeError:
                print("Broken Pipe. Client must have exited")

    @staticmethod
    def find_client_channel(client, fd):
        if client.tcp_fd == fd:
            return Channel.TCP
        elif client.udp_fd == fd:
            return Channel.UDP
        elif client.multicast_fd == fd:
            return Channel.MULTICAST
        else:
            return ValueError('Channel not supported!')

    def get_recipients(self, nickname):
        nicks = [n for n in self.clients.keys() if n != nickname]
        recipients = [self.clients[n] for n in nicks]

        print("recipient nicks: " + str([n for n in nicks]))
        print("recipient udp_addresses: " + str([x.udp_address for x in recipients]))

        return recipients

    @staticmethod
    def create_msg_from(nickname, msg):
        return str(nickname + ": " + msg)

    def close_socket(self):
        for client in self.clients:
            client.send_message_via_tcp('SERVER: Closing connection')
            client.close()
        self.tcp_sock.close()
        print("Server exiting")

    def run(self):
        connection_worker = threading.Thread(target=self.accept_connections)
        poll_worker = threading.Thread(target=self.poll)
        connection_worker.start()
        poll_worker.start()

    def accept_connections(self):
        while True:
            client_tcp_socket, address = self.accept_connection()
            self.register_tcp_client(client_tcp_socket, address)

    def poll(self):
        try:
            while True:
                events = self.e.poll(self.EPOLL_TIMEOUT)
                for fd, event_type in events:
                    if event_type & select.EPOLLIN:
                        print("fd: " + str(fd))
                        for nickname in self.clients.keys():
                            client = self.clients[nickname]
                            if client.tcp_fd == fd:
                                t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                     args=(nickname, Channel.TCP))
                                t.start()
                                break
                            elif client.udp_fd == fd:
                                t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                     args=(nickname, Channel.UDP))
                                t.start()
                                break
                            elif client.multicast_fd == fd:
                                t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                     args=(nickname, Channel.MULTICAST))
                                t.start()
                                break
                        else:  # first time msg is sent via UDP or MULTICAST???? TODO!!!!
                            print("Client not registered yet. Let's do it")
                            self.register_udp_or_multicast_client(fd)
                            # we don't send client's nickname to all people

        except KeyboardInterrupt:
            print('Exiting')
