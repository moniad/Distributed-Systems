import select
import socket
import threading

from enum import Enum


class Channel(Enum):
    TCP = 'T',
    UDP = 'U',
    MULTICAST = 'M'


class MySocket:
    MSG_SIZE = 1024
    SERVER_IP = '127.0.0.1'
    MULTICAST_IP = '224.0.0.7'  # todo
    TCP_PORT = 8127
    UDP_PORT = 9091
    EPOLL_TIMEOUT = 1

    def __init__(self):
        self.tcp_sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.udp_sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    def send_message_via_tcp(self, message):
        self.tcp_sock.send(bytes(message, 'utf8'))

    def send_message_via_udp(self, message, addr):
        self.udp_sock.sendto(bytes(message, 'utf8'), addr)

    def receive_message_via_tcp(self, sock):
        return str(sock.recv(self.MSG_SIZE), 'utf8')

    def receive_message_via_udp(self):
        msg, addr = self.udp_sock.recvfrom(self.MSG_SIZE)
        return str(msg, 'utf8'), addr

    def close_socket(self):
        print("Default Exiting!!")
        # self.tcp_sock.close()


class ClientSocket(MySocket):
    def __init__(self, nickname):
        super().__init__()
        self.tcp_sock.connect((self.SERVER_IP, self.TCP_PORT))
        self.nickname = nickname
        self.e = select.epoll()

        # tcp
        fd = self.tcp_sock.fileno()
        self.e.register(fd, select.EPOLLIN)

        self.channels = dict()
        self.channels[fd] = self.tcp_sock
        # self.channels[self.multicast_sock.fileno()] = self.multicast_sock todo

        self.has_sent_via_udp = False
        self.has_sent_via_multicast = False

        self.run()

    def __str__(self):
        return self.nickname

    def close_socket(self):
        self.tcp_sock.close()
        print("Client exiting")

    def run(self):
        sender = threading.Thread(target=self.sender)
        poll_worker = threading.Thread(target=self.poll)
        sender.start()
        poll_worker.start()

    def sender(self):
        self.send_message_via_tcp(nickname)

        try:
            while True:
                msg = input()
                if msg == 'q':
                    print("Quitting...")
                    break
                elif msg == 'u':
                    msg = input('Give me your UDP msg: ')
                    if not self.has_sent_via_udp:
                        self.send_message_via_udp(nickname, (self.SERVER_IP, self.UDP_PORT))
                        print('[UDP] You sent your NICKNAME: ' + nickname)
                        self.has_sent_via_udp = True

                    self.send_message_via_udp(msg, (self.SERVER_IP, self.UDP_PORT))
                    print('[UDP] You sent a message: ' + msg)

                elif msg == 'm':
                    msg = input('Give me your MULTICAST msg: ')
                    if not self.has_sent_via_multicast:
                        # self.send_message_via_multicast(nickname, (self.MULTICAST_IP, self.UDP_PORT)) todo
                        self.has_sent_via_multicast = True
                    # self.send_message_via_multicast(msg, (self.MULTICAST_IP, self.UDP_PORT)) todo
                    print('[MULTICAST] You sent a message: ' + msg)
                else:
                    self.send_message_via_tcp(msg)
                    print('[TCP] You sent a message: ' + msg)

        except KeyboardInterrupt:
            self.close_socket()

    def poll(self):
        try:
            while True:
                events = self.e.poll(self.EPOLL_TIMEOUT)
                for fd, event_type in events:
                    if event_type & select.EPOLLIN:
                        print(self.receive_message_via_tcp(self.channels[fd]))
        except KeyboardInterrupt:
            print('Exiting')


if __name__ == '__main__':
    nickname = input('Give me your nickname: ')
    print("Now you can use this chat!")

    client_socket = ClientSocket(nickname)
