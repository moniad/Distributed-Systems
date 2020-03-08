# import socket
# import threading
# from time import sleep
#
# BUFFER_SIZE = 1024
#
# PORT = 8008
# server_IP = "localhost"
#
# tcp_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# tcp_server.connect((server_IP, PORT))
# can_receive_messages = False
#
# udp_server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
#
#
# def tcp_sender():
#     tcp_server.send(bytes(nickname, 'utf8'))
#
#     while True:
#         msg = input()
#         if msg == 'q':
#             print("Quitting...")
#             break
#         elif msg == 'u':
#             udp_sender()
#         else:
#             tcp_server.send(bytes(msg, 'utf8'))
#         # print('You sent a message: ' + msg)
#     tcp_server.close()
#
#
# def tcp_receiver():
#     while True:
#         data = tcp_server.recv(1024)
#         print(str(data, 'utf8'))
#
#
# def udp_sender():
#     udp_server.sendto(bytes(nickname, 'utf8'), (server_IP, PORT))
#
#     msg = input()
#     if msg == 'q':
#         print("Quitting...")
#
#     udp_server.sendto(bytes(msg, 'utf8'), (server_IP, PORT))
#
#
# def udp_receiver():
#     while True:
#         data, addr = udp_server.recvfrom(BUFFER_SIZE)
#         print(str(data, 'utf8'))
#
#

import socket

# right now only for TCP


class MySocket:
    MSG_SIZE = 1024
    SERVER_IP = 'localhost'
    PORT = 8087
    DEFAULT_NICKNAME = 'default_nickname'

    def __init__(self):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def send_message(self, message):
        self.sock.send(bytes(message, 'utf8'))

    def receive_message(self, sock):
        return str(sock.recv(self.MSG_SIZE), 'utf8')  # adjust to epoll

    def close_socket(self):
        print("Default Exiting!!")
        # self.sock.close()


class ClientSocket(MySocket):
    def __init__(self, nickname):
        super().__init__()
        self.sock.connect((self.SERVER_IP, self.PORT))
        self.nickname = nickname
        self.run()

    def __str__(self):
        return self.nickname

    def close_socket(self):
        self.sock.close()
        print("Client exiting")

    def run(self):
        self.send_message(nickname)

        try:
            while True:
                msg = input()
                if msg == 'q':
                    print("Quitting...")
                    break
                elif msg == 'u':
                    # todo: send via udp!
                    pass
                else:
                    self.send_message(msg)
                print('You sent a message: ' + msg)
        except KeyboardInterrupt as e:
            self.close_socket()


if __name__ == '__main__':
    nickname = input('Give me your nickname: ')
    print("Now you can use this chat!")

    client_socket = ClientSocket(nickname)

#     todo: add client_sender_worker who checks for message == 'U' - if so, then message is sent via UDP socket
#
#     tcp_sending_thread = threading.Thread(target=tcp_sender)
#     tcp_receiving_thread = threading.Thread(target=tcp_receiver)
#
#     udp_sending_thread = threading.Thread(target=udp_sender)
#     udp_receiving_thread = threading.Thread(target=udp_receiver)
#
#     tcp_sending_thread.start()
#     tcp_receiving_thread.start()
#
#     while True:
#         sleep(5)
#         pass
#
#     # server.close()
