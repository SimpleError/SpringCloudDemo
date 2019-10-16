package com.example.rabbitmq.workqueue.fanout;

import com.example.rabbitmq.workqueue.ConnectionUtils;
import com.example.rabbitmq.workqueue.QueueUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer2Test {

    static Channel getChannel() {
        Channel channel = null;
        try {
            Connection connection = ConnectionUtils.getConnection();

            channel = connection.createChannel();
            channel.basicQos(3);

            channel.queueDeclare(QueueUtil.FANOUT_QUEUE2, true, false, false, null);
            channel.queueBind(QueueUtil.FANOUT_QUEUE2, QueueUtil.FANOUT_EXCHANGE, "");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void main(String[] args) {
        try {
            Channel channel = getChannel();

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("t1 Received '" + message + "'");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            channel.basicConsume(QueueUtil.FANOUT_QUEUE2, false, deliverCallback, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
