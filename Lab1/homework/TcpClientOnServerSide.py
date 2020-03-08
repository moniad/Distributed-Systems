from Lab1.homework.ClientOnServerSide import ClientOnServerSide


class TcpClientOnServerSide(ClientOnServerSide):  # contains TCP clients' data
    def __init__(self, tcp_sock, nickname):
        super().__init__(tcp_sock, nickname)

    def __str__(self):
        return "tcp_sock: " + str(self.sock_or_addr) + ", nickname: " + str(self.nickname)

    def send_message_via_tcp(self, message):
        self.sock_or_addr.send(bytes(message, 'utf8'))
