package com.example.rabbitmq.workqueue.topic;

import com.example.rabbitmq.workqueue.QueueUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductTest {


    /**
     * FANOUT 不需要routing_key
     */
    @Test
    public void test() throws IOException, TimeoutException {
        int i = 0;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.43.247");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(QueueUtil.TOPIC_EXCHANGE, BuiltinExchangeType.TOPIC);
        while(i < 10){
            String message = "Hello World - " + i;
            channel.basicPublish(QueueUtil.TOPIC_EXCHANGE, QueueUtil.TOPIC_ROUTING, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            i++;
        }
        channel.close();
        connection.close();
    }
}
