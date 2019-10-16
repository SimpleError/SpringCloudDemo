package com.example.rabbitmq.workqueue.Single;

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
                while (true) {
                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                throws IOException {
                            String message = new String(body, "UTF-8");
                            System.out.println("t1 ... Consumer have received '" + message + "'");
                            try {
                                Thread.sleep(100L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }
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
                while (true) {
                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                throws IOException {
                            String message = new String(body, "UTF-8");
                            System.out.println("t2 ... Consumer have received '" + message + "'");
                            try {
                                Thread.sleep(200L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();

        countDownLatch.await();
    }
}
