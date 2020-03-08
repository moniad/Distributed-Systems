class ClientOnServerSide:  # contains client data
    def __init__(self, sock, address, nickname):
        self.sock = sock
        self.address = address
        self.nickname = nickname
