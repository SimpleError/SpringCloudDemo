package com.example.rabbitmq.workqueue.queue;

import com.example.rabbitmq.workqueue.QueueUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class ConsumerTest {
    private final static String QUEUE_NAME = "hello";


    @Test
    public void test() throws IOException, TimeoutException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("192.168.43.247");
                factory.setUsername("admin");
                factory.setPassword("admin");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
                channel.queueDeclare(QueueUtil.QUEUE_QUEUE_NAME, true, false, false, null);
                channel.basicQos(1);
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");
                        System.out.println("t1 ... Consumer have received '" + message + "'");
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                };
                channel.basicConsume(QueueUtil.QUEUE_QUEUE_NAME, false, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("192.168.43.247");
                factory.setUsername("admin");
                factory.setPassword("admin");
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();

                channel.queueDeclare(QueueUtil.QUEUE_QUEUE_NAME, true, false, false, null);

                channel.basicQos(1);
                Consumer consumer = new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                            throws IOException {
                        String message = new String(body, "UTF-8");
                        System.out.println("t2 ... Consumer have received '" + message + "'");
                        try {
                            Thread.sleep(30000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                };
                channel.basicConsume(QueueUtil.QUEUE_QUEUE_NAME, false, consumer);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }
}
