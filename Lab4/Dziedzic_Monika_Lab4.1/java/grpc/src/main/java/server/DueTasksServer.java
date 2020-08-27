package server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class DueTasksServer {
    private static final int PORT = 8080;
    private static Server server;

    public DueTasksServer(int port) {
        server = ServerBuilder.forPort(port)
                .addService(new ProviderImpl())
                .addService(new HealthImpl())
                .build();
    }

    public static void startServer() throws InterruptedException, IOException {
        server.start();
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Starting server on port " + PORT);

        server = ServerBuilder.forPort(PORT)
                .addService(new HealthImpl())
                .addService(new ProviderImpl())
                .build();

        startServer();
    }
}
