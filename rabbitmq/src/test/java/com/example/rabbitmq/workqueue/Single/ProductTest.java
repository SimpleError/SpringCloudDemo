package com.example.rabbitmq.workqueue.Single;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductTest {

    private final static String QUEUE_NAME = "hello";

    @Test
    public void test() throws IOException, TimeoutException, InterruptedException {
        int i = 0;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.43.247");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        while(i < 10000){
            String message = "Hello World - " + i;
            channel.basicPublish("", "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            i++;
        }
        channel.close();
        connection.close();
    }
}
