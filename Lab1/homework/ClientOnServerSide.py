class ClientOnServerSide:  # contains client data
    def __init__(self, tcp_sock, tcp_fd, udp_address=None, udp_fd=None, multicast_address=None, multicast_fd=None):
        self.tcp_sock = tcp_sock
        self.tcp_fd = tcp_fd

        self.udp_address = udp_address
        self.udp_fd = udp_fd

        self.multicast_address = multicast_address
        self.multicast_fd = multicast_fd

    def __str__(self):
        return "tcp_sock: " + str(self.tcp_sock) + ", tcp_fd: " + str(self.tcp_fd) + ", udp_addr: " + str(
            self.udp_address) + ", udp_fd: " + str(self.udp_fd) + ", multicast_addr: " + str(
            self.multicast_address) + ", mutlicast_fd: " + str(self.multicast_fd)

    def send_message_via_tcp(self, message):
        self.tcp_sock.send(bytes(message, 'utf8'))
