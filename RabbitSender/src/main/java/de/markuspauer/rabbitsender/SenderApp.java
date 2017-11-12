package de.markuspauer.rabbitsender;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author Markus Pauer <mpauer@beit.de>
 */
public class SenderApp {

    private final String EXCHANGE_NAME = "MyExchange";
    private final Connection connection;
    private final Channel channel;

    public SenderApp() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        connection = factory.newConnection();

        channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
    }
    
    public void sendMessage(String message) throws IOException {
        channel.basicPublish(EXCHANGE_NAME, "process", null, message.getBytes());
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws java.util.concurrent.TimeoutException
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        SenderApp app = new SenderApp();
        app.sendMessage("Hallo erstmal");
        System.out.println("Sende Nachricht");
        app.close();
    }

}
