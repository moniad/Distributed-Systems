import select
import threading

from Lab1.homework.PythonTcpClient import MySocket, Channel
from Lab1.homework.TcpClientOnServerSide import TcpClientOnServerSide
from Lab1.homework.UdpClientOnServerSide import UdpClientOnServerSide


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5

    def __init__(self):
        super().__init__()

        self.tcp_clients = dict()  # dicts of ClientsOnTheServerSide, identifying clients by fd (key in dict)
        self.udp_clients = dict()
        self.multicast_clients = dict()

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

        self.tcp_clients[fd] = TcpClientOnServerSide(client_socket, nickname)

    def register_udp_or_multicast_client(self, fd):
        if fd == self.udp_sock.fileno():
            nickname, addr = self.receive_message_via_udp()
            new_client = UdpClientOnServerSide(addr, nickname)
            self.udp_clients[fd] = new_client
        else:  # if fd == self.multicast_sock.fileno():  # multicast todo
            print("MULTICAST")
            # nickname, addr = self.receive_message_via_multicast() todo
            # new_client = MulticastClientOnServerSide(addr, nickname)
            # self.multicast_clients[fd] = new_client
            pass
        # else: todo
        #     print("Unknown sevices")

    def send_msg_to_all_clients_excluding_sender(self, fd, channel):
        if channel == Channel.TCP:
            client = self.tcp_clients[fd]
            msg = self.receive_message_via_tcp(client.sock_or_addr)
        elif channel == Channel.UDP:
            client = self.udp_clients[fd]
            msg, addr = self.receive_message_via_udp()
        else:  # multicast
            client = self.multicast_clients[fd]
            msg, addr = 'DEFAULT MULTICAST MSG!!! FIXME', 'addr'  #todo

        print('client data:  ' + str(client))
        msg_to_send = ServerSocket.create_msg_from(client.nickname, msg)
        print("msg_to_send: " + msg_to_send)

        recipients = ServerSocket.get_recipients(self, fd, channel)

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

    # @staticmethod
    # def find_client_channel(client, fd):
    #     if client.tcp_fd == fd:
    #         return Channel.TCP
    #     elif client.udp_fd == fd:
    #         return Channel.UDP
    #     elif client.multicast_fd == fd:
    #         return Channel.MULTICAST
    #     else:
    #         return ValueError('Channel not supported!')

    def get_recipients(self, fd, channel):
        if channel == Channel.TCP:
            fds = [tcp_fd for tcp_fd in self.tcp_clients.keys() if tcp_fd != fd]
            recipients = [self.tcp_clients[tcp_fd] for tcp_fd in fds]
        elif channel == Channel.UDP:
            fds = [udp_fd for udp_fd in self.udp_clients.keys() if udp_fd != fd]
            recipients = [self.udp_clients[udp_fd] for udp_fd in fds]
        else:  # multicast
            fds = [multicast_fd for multicast_fd in self.multicast_clients.keys() if multicast_fd != fd]
            recipients = [self.multicast_clients[multicast_fd] for multicast_fd in fds]

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
                        if fd in self.tcp_clients.keys():
                            t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                 args=(fd, Channel.TCP))
                            t.start()
                            print("TCP - found")
                            break
                        elif fd in self.udp_clients.keys():
                            t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                 args=(fd, Channel.UDP))
                            t.start()
                            print("UDP - found")
                        elif fd in self.multicast_clients.keys():
                            t = threading.Thread(target=self.send_msg_to_all_clients_excluding_sender,
                                                 args=(fd, Channel.MULTICAST))
                            t.start()
                            print("MULTI - found")
                            break
                        else:
                            print("Client not registered yet. Let's do it")
                            self.register_udp_or_multicast_client(fd)
                            # we don't send client's nickname to all people

        except KeyboardInterrupt:
            print('Exiting')
