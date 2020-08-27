import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Z2_Producer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z2 PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
//        String DIRECT_EXCHANGE_NAME = "exchangeDirect";
        String TOPIC_EXCHANGE_NAME = "exchangeTopic";
//        channel.exchangeDeclare(DIRECT_EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(TOPIC_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        while (true) {

            // read msg
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter [routingKey, message]: ");
            String[] keyAndMessages = br.readLine().split(",");
            String routingKey = keyAndMessages[0];
            String message = keyAndMessages[1];

            // break condition
            if ("exit".equals(message)) {
                break;
            }

            // publish
//            channel.basicPublish(DIRECT_EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            channel.basicPublish(TOPIC_EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }
    }
}
