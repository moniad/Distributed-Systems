import socket
import threading
from time import sleep

PORT = 8015
BUFFER_SIZE = 1024
DEFAULT_NICKNAME = 'basic_nickname'


def worker(address):
    cl = clients[address][0]
    nickname = str(cl.recv(BUFFER_SIZE), 'utf8')  # first message is always a nickname
    clients[address] = (cl, nickname)
    while True:
        # (wait to) receive message from client
        buff = cl.recv(BUFFER_SIZE)

        print(
            "python tcp server received msg: " + str(buff, 'utf8') + " from " + nickname)

        if not buff:
            print(nickname + ' exits')
            break

        send_message_to_all_clients_excluding(buff, nickname, address)

    del clients[address]
    cl.close()


def send_message_to_all_clients_excluding(buff, nickname, address):
    msg_to_send = nickname + ": " + str(buff, 'utf8')
    print("msg_to_send: " + msg_to_send)

    recipients = [p for p in clients.keys() if p != address]
    print("recipients: " + str(recipients))

    for recip in recipients:
        clients[recip][0].send(bytes(msg_to_send, 'utf8'))
        print("Sent message: " + msg_to_send + " to " + str(recip))


def udp_worker():
    nickname = DEFAULT_NICKNAME
    while nickname == DEFAULT_NICKNAME:
        pass

    buff, addr = server_udp_socket.recvfrom(BUFFER_SIZE)
    nickname = str(buff, 'utf8')

    while True:
        message, _ = server_udp_socket.recvfrom(BUFFER_SIZE)
        print("python udp server received msg: " + str(buff, 'utf8'))
        send_message_to_all_clients_excluding(message, nickname, addr)


if __name__ == '__main__':
    # open_tcp_connection
    server_tcp_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_tcp_socket.bind(('', PORT))
    server_tcp_socket.listen()

    # open_udp_connection
    server_udp_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_udp_socket.bind(('', PORT))

    udp_t = threading.Thread(target=udp_worker)
    udp_t.start()

    clients = dict()  # address -> thread_no

    print('PYTHON TCP SERVER')

    while True:
        client_tcp_socket, address = server_tcp_socket.accept()

        print("New client: " + str(address[0]) + ":" + str(address[1]) + "!")

        clients[address] = (client_tcp_socket, DEFAULT_NICKNAME)

        users = [c[1] for c in clients.values() if c[1] != DEFAULT_NICKNAME]
        client_tcp_socket.send(bytes("Available users: " + str(users), 'utf8'))

        t = threading.Thread(target=worker, args=(address,))
        t.start()

        sleep(2)
        if not len(clients):  # if all clients are gone
            server_tcp_socket.close()
            break
# todo: ctrl + C handling
