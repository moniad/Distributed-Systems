import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Z1_Producer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z1 PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

// beginning of 1.a
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // producer (publish msg)
//        String message = br.readLine();
// endof 1.a

// 1. b
        String[] msg = new String[]{"1", "5"};
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", QUEUE_NAME, null, msg[i % 2].getBytes());
            System.out.println("Sent: " + msg[i%2]);
        }


        // close
        channel.close();
        connection.close();
    }
}
