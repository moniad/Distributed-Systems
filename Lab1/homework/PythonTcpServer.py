import socket
import threading
from time import sleep

TCP_PORT = 8014
BUFFER_SIZE = 1024


def worker(address):
    cl = clients[address][0]
    nickname = str(cl.recv(BUFFER_SIZE), 'utf8')  # first message is always a nickname
    clients[address] = (cl, nickname)
    while True:
        # (wait to) receive message from client
        buff = cl.recv(BUFFER_SIZE)

        print(
            "python udp server received msg: " + str(buff, 'utf8') + " from " + nickname)

        if not buff:
            print(nickname + ' exits')
            break

        msg_to_send = nickname + ": " + str(buff, 'utf8')
        print("msg_to_send: " + msg_to_send)

        recipients = [p for p in clients.keys() if p != address]
        print("recipients: " + str(recipients))

        for recip in recipients:
            clients[recip][0].send(bytes(msg_to_send, 'utf8'))
            print("Sent message: " + msg_to_send + " to " + str(recip))

    del clients[address]
    cl.close()


if __name__ == '__main__':

    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('', TCP_PORT))
    server_socket.listen()

    clients = dict()  # address -> thread_no

    print('PYTHON UDP SERVER')

    while True:
        client_socket, address = server_socket.accept()

        print("New client: " + str(address[0]) + ":" + str(address[1]) + "!")

        clients[address] = (client_socket, '')

        users = [c[1] for c in clients.values() if c[1] != '']
        client_socket.send(bytes("Available users: " + str(users), 'utf8'))

        t = threading.Thread(target=worker, args=(address,))
        t.start()

        sleep(2)
        if not len(clients):  # if all clients are gone
            server_socket.close()
            break
# todo: ctrl + C handling
