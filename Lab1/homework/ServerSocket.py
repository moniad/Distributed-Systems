import select
import threading

from Lab1.homework.PythonTcpClient import MySocket, Channel
from Lab1.homework.TcpClientOnServerSide import TcpClientOnServerSide


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5

    def __init__(self):
        super().__init__()

        self.tcp_clients = dict()  # dicts of ClientsOnTheServerSide, identifying clients by fd (key in dict)
        self.multicast_clients = dict()

        self.tcp_sock.bind((self.SERVER_IP, self.PORT))
        self.tcp_sock.listen(ServerSocket.MAX_CLIENTS_COUNT)

        self.e = select.epoll()  # edge polling object

        self.udp_sock.bind((self.SERVER_IP, self.PORT))
        # self.e.register(self.udp_sock.fileno(), select.EPOLLIN)  # todo: is it enough?

        self.run()

    def accept_connection(self):
        return self.tcp_sock.accept()

    def register_tcp_client(self, client_socket, address):
        # client's nickname is always the first message. Client won't connect if the nickname is unknown,
        # so we won't have to wait unless there are any network problems
        nickname = self.receive_message_via_tcp(client_socket)
        print("New client: " + nickname + " " + str(address))  # [0]) + ":" + str(address[1][) + "!")

        fd = client_socket.fileno()
        # register client socket for reading
        self.e.register(fd, select.EPOLLIN)

        self.tcp_clients[fd] = TcpClientOnServerSide(client_socket, address, nickname)

    # def register_udp_or_multicast_client(self, fd):
    #     if fd == self.udp_sock.fileno():
    #         nickname, addr = self.receive_message_via_udp()
    #         new_client = UdpClientOnServerSide(addr, nickname)
    #         self.udp_clients[fd] = new_client
    #         self.e.register(addr.fileno(), select.EPOLLIN)
    #     else:  # if fd == self.multicast_sock.fileno():  # multicast todo
    #         print("MULTICAST")
    #         # nickname, addr = self.receive_message_via_multicast() todo
    #         # new_client = MulticastClientOnServerSide(addr, nickname)
    #         # self.multicast_clients[fd] = new_client
    #         # self.e.register(addr.fileno(), select.EPOLLIN)
    #         pass
    #     # else: todo
    #     #     print("Unknown sevices")

    def send_msg_to_all_clients_excluding_sender(self, fd):
        client, channel = self.find_client_and_channel(fd)
        if channel == Channel.TCP:
            msg = self.receive_message_via_tcp(client.tcp_sock)
        elif channel == Channel.UDP:
            if client is None:
                nickname, addr = self.receive_message_via_udp()
                print("WELCOME THE NEW UDP CLIENT: " + nickname)
                self.e.register(addr.fileno(), select.EPOLLIN)
                return
            else:
                msg, addr = self.receive_message_via_udp()
        else:  # multicast
            msg, addr = 'DEFAULT MULTICAST MSG!!! FIXME', 'addr'  # todo

        msg_to_send = ServerSocket.create_msg_from(client.nickname, msg)
        print("msg_to_send: " + msg_to_send)

        recipients = ServerSocket.get_recipients(self, fd)

        for recip in recipients:
            try:
                if channel == Channel.TCP:
                    recip.send_message_via_tcp(msg_to_send)
                elif channel == Channel.UDP:
                    print("recipeint address: " + str(recip.address) + " vs address: " + str(addr))
                    self.send_message_via_udp(msg_to_send, recip.address)
                else:  # multicast
                    # todo!
                    # self.send_message_via_multicast(msg_to_send, recip.multicast_address)
                    pass

                # print("Sent message: \"" + msg_to_send + "\" to " + recip.nickname)
            except BrokenPipeError:
                print("Broken Pipe. Client must have exited")

    def find_client_and_channel(self, fd):
        if self.tcp_sock.fileno() == fd:
            return self.tcp_clients[fd], Channel.TCP
        else:  # if self.udp_sock.fileno() == fd:
            return None, Channel.UDP  # first message ever on UDP channel, we don't know the message yet :(
        # else:
        #     clients = self.tcp_clients.values()
        #     for c in clients:
        #         if fd == c.address.file_no():
        #             return c, Channel.UDP
        # elif self.multicast_sock.fileno() == fd: todo
        #     return Channel.MULTICAST
        # else:
        # return ValueError('Channel not supported!')

    def get_recipients(self, fd):
        fds = [tcp_fd for tcp_fd in self.tcp_clients.keys() if tcp_fd != fd]
        recipients = [self.tcp_clients[tcp_fd] for tcp_fd in fds]

        print("recipient nicks: " + str([r.nickname for r in recipients]))
        # print("recipient udp_addresses: " + str([x.udp_address for x in recipients]))
        # print("FIRST recipient addr: " + str(recipients[0].udp_address))

        return recipients

    @staticmethod
    def create_msg_from(nickname, msg):
        return str(nickname + ": " + msg)

    # def close_socket(self):
    #     for client in self.tcp_clients:
    #         client.send_message_via_tcp('SERVER: Closing connection')
    #         client.close()
    #     self.tcp_sock.close()
    #     print("Server exiting")

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
            self.register_tcp_client(client_tcp_socket, address)

    def udp_worker(self):
        while True:
            msg, address = self.receive_message_via_udp()
            clients = self.tcp_clients
            client = None
            for c in clients:
                if c.address == address:
                    client = c
                    break

            msg_to_send = ServerSocket.create_msg_from(client.nickname, msg)
            print("msg_to_send: " + msg_to_send)

            addresses = [a for a in self.tcp_clients.values() if a != address]

            for address in addresses:
                print("address: " + str(address))

                print("Sent message: \"" + msg_to_send + "\" to " + str(address))
                self.send_message_via_udp(msg_to_send, address)

    def poll(self):
        try:
            while True:
                events = self.e.poll(self.EPOLL_TIMEOUT)
                for fd, event_type in events:
                    if event_type & select.EPOLLIN:
                        print("fd: " + str(fd))
                        if fd in self.tcp_clients.keys():
                            t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                 args=(fd,))
                            t.start()

                        # elif fd in self.udp_clients.keys():
                        #     t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                        #                          args=(fd, Channel.UDP))
                        #     t.start()
                        #     print("UDP - found")
                        # elif fd in self.multicast_clients.keys():
                        #     t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                        #                          args=(fd, Channel.MULTICAST))
                        #     t.start()
                        #     print("MULTI - found")
                        #     break
                        else:
                            print("IMPOSSIBLE")
                            # self.register_udp_or_multicast_client(fd)
                            # we don't send client's nickname to all people

        except KeyboardInterrupt:
            print('Exiting')
