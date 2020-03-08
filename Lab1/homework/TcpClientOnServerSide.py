# from Lab1.homework.ClientOnServerSide import ClientOnServerSide


class TcpClientOnServerSide:  # contains TCP clients' data
    def __init__(self, tcp_sock, address, nickname):
        self.tcp_sock = tcp_sock
        self.address = address
        self.nickname = nickname

    def __str__(self):
        return super().__str__() + ", tcp_sock: " + str(self.tcp_sock)

    def send_message_via_tcp(self, message):
        self.tcp_sock.send(bytes(message, 'utf8'))
