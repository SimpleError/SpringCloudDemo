package com.example.rabbitmq.workqueue.topic;

import com.example.rabbitmq.workqueue.QueueUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer3Test {
    private final static String QUEUE_NAME = "hello";

    private static Channel getChannel() {
        Channel channel = null;
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.43.247");
            factory.setUsername("admin");
            factory.setPassword("admin");
            Connection connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QueueUtil.TOPIC_QUEUE3, true, false, false, null);
            channel.queueBind(QueueUtil.TOPIC_QUEUE3, QueueUtil.TOPIC_EXCHANGE, QueueUtil.TOPIC_BINDING3);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void main(String[] args) {
        try {
            Channel channel = Consumer3Test.getChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" t3 Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
            };
            channel.basicConsume(QueueUtil.TOPIC_QUEUE3, true, deliverCallback, consumerTag -> {
                System.out.println("fall back ...");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
