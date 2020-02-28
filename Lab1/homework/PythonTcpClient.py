import socket
import threading

server_TCP_PORT = 8014
server_IP = "127.0.0.1"

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.connect((server_IP, server_TCP_PORT))
can_receive_messages = False


def sender():
    nickname = input('Give me your nickname: ')
    server.send(bytes(nickname, 'utf8'))
    global can_receive_messages
    can_receive_messages = True

    while True:
        msg = input()
        if msg == 'q':
            print("Quitting...")
            break
        server.send(bytes(msg, 'utf8'))
        # print('You sent a message: ' + msg)
    server.close()


def receiver():
    while not can_receive_messages:
        pass
    print("Now you can use this chat!")
    while True:
        data = server.recv(1024)
        print(str(data, 'utf8'))


if __name__ == '__main__':

    sending_thread = threading.Thread(target=sender)
    receiving_thread = threading.Thread(target=receiver)

    sending_thread.start()
    receiving_thread.start()

    while True:
        pass

    # server.close()
