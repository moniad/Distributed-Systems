import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Z2_Consumer {

    public static void main(String[] argv) throws Exception {

        // info
        System.out.println("Z2 CONSUMER");

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


        // queue & bind
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter routingKey: ");
        String routingKey = br.readLine();

        String queueName = channel.queueDeclare().getQueue();
//        channel.queueBind(queueName, DIRECT_EXCHANGE_NAME, routingKey);
        channel.queueBind(queueName, TOPIC_EXCHANGE_NAME, routingKey);
        System.out.println("created queue: " + queueName);


        // consumer (message handling)
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        channel.basicConsume(queueName, true, consumer);
    }
}
