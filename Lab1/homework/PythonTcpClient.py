import socket
import threading
from time import sleep

BUFFER_SIZE = 1024

PORT = 8015
server_IP = "127.0.0.1"

tcp_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp_server.connect((server_IP, PORT))
can_receive_messages = False

udp_server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


def tcp_sender():
    tcp_server.send(bytes(nickname, 'utf8'))

    while True:
        msg = input()
        if msg == 'q':
            print("Quitting...")
            break
        elif msg == 'u':
            udp_sender()
        else:
            tcp_server.send(bytes(msg, 'utf8'))
        # print('You sent a message: ' + msg)
    tcp_server.close()


def tcp_receiver():
    while True:
        data = tcp_server.recv(1024)
        print(str(data, 'utf8'))


def udp_sender():
    udp_server.sendto(bytes(nickname, 'utf8'), (server_IP, PORT))

    msg = input()
    if msg == 'q':
        print("Quitting...")

    udp_server.sendto(bytes(msg, 'utf8'), (server_IP, PORT))


def udp_receiver():
    while True:
        data, addr = udp_server.recvfrom(BUFFER_SIZE)
        print(str(data, 'utf8'))


if __name__ == '__main__':
    nickname = input('Give me your nickname: ')
    print("Now you can use this chat!")

    tcp_sending_thread = threading.Thread(target=tcp_sender)
    tcp_receiving_thread = threading.Thread(target=tcp_receiver)

    udp_sending_thread = threading.Thread(target=udp_sender)
    udp_receiving_thread = threading.Thread(target=udp_receiver)

    tcp_sending_thread.start()
    tcp_receiving_thread.start()

    while True:
        sleep(5)
        pass

    # server.close()
