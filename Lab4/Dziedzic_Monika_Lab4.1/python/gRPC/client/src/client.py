import time
from datetime import datetime
from threading import Thread

import ConnectionHealth_pb2 as ConnectionHealth_pb2
import ConnectionHealth_pb2_grpc as ConnectionHealth_pb2_grpc
import DueTasksProvider_pb2 as DueTasksProvider_pb2
import DueTasksProvider_pb2_grpc as DueTasksProvider_pb2_grpc
import grpc

HOST = 'localhost'
PORT = '8080'
CONNECTED = False
SUBSCRIBED = False
health_stub = None


class Client:
    def __init__(self, name):
        self.name = name

    def subscribe(self, stub, subject):
        global SUBSCRIBED

        print("[INFO] Choose event type")
        for x in choice:
            print(x)
        selected_event = handle_event_selection()

        pack = DueTasksProvider_pb2.Subscription(userName=self.name, subject=subject, type=selected_event)
        res = stub.subscribe(pack, wait_for_ready=True)
        if res.status == DueTasksProvider_pb2.SubscriptionStatus.Value('ERROR'):
            print("[ERROR] Invalid request")
            print("[MESSAGE]", res.dueTaskMessage)
            return

        if not SUBSCRIBED:
            SUBSCRIBED = True
            print("[INFO] Subscribed")
            self.listen(stub)

    def listen(self, stub):
        thread = Thread(target=callback, args=(stub, self.name))
        thread.start()

    def run(self):
        global health_stub
        global CONNECTED
        associated = HOST + ":" + PORT
        with grpc.insecure_channel(associated) as channel:
            health_stub = ConnectionHealth_pb2_grpc.HealthStub(channel)
            stub = DueTasksProvider_pb2_grpc.ProviderStub(channel)
            print("[INFO] Available subjects: ")
            for s in subjects:
                print(s)
            print("[INFO] Enter subject to subscribe:")
            try:
                while True:
                    user_input = input("> ")
                    if user_input == 'exit':
                        print("[STOP] Exiting...")
                        CONNECTED = True
                        break
                    self.subscribe(stub, user_input)
            except KeyboardInterrupt:
                CONNECTED = True
                print("[STOP] Got SIGINT... Exiting")


def get_event_name(event_type):
    return DueTasksProvider_pb2.EventType.Name(event_type)


subjects = [DueTasksProvider_pb2.Subject.Name(x) for x in range(0, 3)]

choice = [str(i + 1) + ": " + get_event_name(event_number) for i, event_number in enumerate(range(0, 3))]


def verbose_runner():
    global HOST
    global PORT
    client_input = input("Client name: ")
    return client_input


def handle_event_selection():
    option = input("> ")
    return [int(option) - 1]


def notification_printer(notification):
    now = str(datetime.now())
    print("[MESSAGE] Received new notification from server")

    subject = notification.subject

    print("================= NEW DUE TASK =================")
    print("     Date:", now)
    print("     Subject:", subject)
    event_type = choice[notification.type][3:]
    print("     Event type:", event_type)
    dueTaskMessage = notification.dueTaskMessage
    print("     Due task message:", dueTaskMessage)
    print("================================================\n")


def server_fault_handler():
    global SUBSCRIBED
    if not health_stub:
        raise ValueError("[ERROR] Server error")

    SUBSCRIBED = False

    retries = 2
    while True:
        try:
            response = health_stub.ping(ConnectionHealth_pb2.PingRequest())
            print("[INFO] Server is alive")
            print("[MESSAGE]", response.message)
            break
        except:
            if CONNECTED:
                print("[ERROR] Health thread exited")
                exit(0)
            print("[ERROR] Pulling exponentially. Server is down")
            time.sleep((2 ** retries) / 100)
            retries += 1


def callback(stub, name):
    try:
        print("[INFO] Listening...")
        res = stub.listen(DueTasksProvider_pb2.SubscriptionRequest(userName=name), wait_for_ready=True)

        for notification in res:
            notification_printer(notification)
    except:
        if CONNECTED:
            print("[STOP] Callback thread exited")
            exit(0)
        print("[INFO] Server closed")
        server_fault_handler()


if __name__ == "__main__":
    client_name = verbose_runner()
    client = Client(client_name)
    client.run()
