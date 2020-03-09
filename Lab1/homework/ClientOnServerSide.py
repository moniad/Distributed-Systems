class ClientOnServerSide:  # contains client data
    def __init__(self, tcp_socket, address, nickname):
        self.tcp_socket = tcp_socket
        self.address = address
        self.nickname = nickname
