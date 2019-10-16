package com.example.rabbitmq.workqueue.direct;

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
     * 生产者指定交换机direct，发送消息时指定routing_key
     */
    @Test
    public void test() throws IOException, TimeoutException, InterruptedException {
        int i = 0;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.43.247");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(QueueUtil.DIRECT_EXCHANGE, BuiltinExchangeType.DIRECT);
        while(i < 50){
            String message = "Hello World - " + i;
            channel.basicPublish(QueueUtil.DIRECT_EXCHANGE, QueueUtil.DIRECT_ROUTING, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            i++;
        }
        channel.close();
        connection.close();
    }
}
