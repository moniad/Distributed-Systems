import threading

from Lab1.homework.ClientOnServerSide import ClientOnServerSide
from Lab1.homework.PythonTcpClient import MySocket


class ServerSocket(MySocket):
    MAX_CLIENTS_COUNT = 5
    clients = dict()  # dict of ClientsOnTheServerSide

    def __init__(self):
        super().__init__()
        self.sock.bind((self.SERVER_IP, self.PORT))  # todo: check if localhost is ok
        self.sock.listen(ServerSocket.MAX_CLIENTS_COUNT)
        self.run()

    def accept_connection(self):
        return self.sock.accept()

    def save_client(self, client_socket, address):
        print("New client: " + str(address[0]) + ":" + str(address[1]) + "!")

        # wait for client's nickname which is always first message
        nickname = self.receive_message(client_socket)  # todo: use epoll!!!!!!!!!!!!!!!!!!!!!!!!!!!
        thread = threading.Thread(target=self.client_worker)  #, args=(address,))  # todo: is args necesary?
        ServerSocket.clients[address] = ClientOnServerSide(client_socket, address, thread, nickname)

        thread.start()

    def client_worker(self):
        pass

    def close_socket(self):
        for client in ServerSocket.clients:
            client.send_message('SERVER: Closing connection')
            client.close()
        self.sock.close()
        print("Server exiting")

    def run(self):
        while True:
            client_tcp_socket, address = self.accept_connection()

            self.save_client(client_tcp_socket, address)
