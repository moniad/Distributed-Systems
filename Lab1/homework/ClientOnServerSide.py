import socket


class ClientOnServerSide:  # contains client data
    def __init__(self, sock, address, thread, nickname):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.address = address
        self.thread = thread
        self.nickname = nickname
