import select
import socket

import threading


class MySocket:
    MSG_SIZE = 1024
    SERVER_IP = 'localhost'
    PORT = 8000
    EPOLL_TIMEOUT = 5

    def __init__(self):
        self.tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

    def send_message_via_tcp(self, message):
        self.tcp_socket.send(bytes(message, 'utf8'))

    def receive_message_via_tcp(self, tcp_socket):
        return str(tcp_socket.recv(self.MSG_SIZE), 'utf8')

    def send_message_via_udp(self, message, addr):
        self.udp_socket.sendto(bytes(message, 'utf8'), addr)

    def receive_message_via_udp(self):
        msg, addr = self.udp_socket.recvfrom(self.MSG_SIZE)
        return str(msg, 'utf8'), addr

    def close_socket(self):
        print("Default Exiting!!")
        # self.tcp_socket.close()


class ClientSocket(MySocket):
    def __init__(self, nickname):
        super().__init__()
        self.tcp_socket.connect((self.SERVER_IP, self.PORT))
        self.nickname = nickname
        self.e = select.epoll()
        fd = self.tcp_socket.fileno()
        self.e.register(fd, select.EPOLLIN)
        self.has_sent_via_udp = False

        # self.channels[self.multicast_sock.fileno()] = self.multicast_sock todo

        self.run()

    def __str__(self):
        return self.nickname

    def close_socket(self):
        self.tcp_socket.close()
        print("Client exiting")

    def run(self):
        sender = threading.Thread(target=self.sender)
        poll_worker = threading.Thread(target=self.poll)
        udp_worker = threading.Thread(target=self.udp_receiver)
        sender.start()
        poll_worker.start()
        udp_worker.start()

    def sender(self):
        self.send_message_via_tcp(nickname)

        try:
            while True:
                msg = input()
                if msg == 'q':
                    print("Quitting...")
                    break
                elif msg == 'u':
                    if not self.has_sent_via_udp:
                        self.send_message_via_udp(nickname, (self.SERVER_IP, self.PORT))
                        print('[UDP] You sent your NICKNAME: ' + nickname)
                        self.has_sent_via_udp = True

                    msg = input('Give me your UDP msg: ')
                    self.send_message_via_udp(msg, (self.SERVER_IP, self.PORT))
                    print('[UDP] You sent a message: ' + msg)
                elif msg == 'm':
                    msg = input('Give me your MULTICAST msg: ')
                    # self.send_message_via_multicast(msg, (self.SERVER_IP, self.PORT))
                    print('[M] You DID NOT SEND a message: ' + msg)
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
                        print(self.receive_message_via_tcp(self.tcp_socket))
        except KeyboardInterrupt:
            print('Exiting')

    def udp_receiver(self):
        while True:
            msg, addr = self.receive_message_via_udp()
            print('[UDP] ' + msg)


if __name__ == '__main__':
    nickname = input('Give me your nickname: ')
    print("Now you can use this chat!")

    client_socket = ClientSocket(nickname)
