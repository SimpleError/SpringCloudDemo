package com.example.rabbitmq.workqueue.fanout;

import com.example.rabbitmq.workqueue.ConnectionUtils;
import com.example.rabbitmq.workqueue.QueueUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ProductTest {


    /**
     * FANOUT 不需要routing_key
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        int i = 0;
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //获取channel
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(QueueUtil.FANOUT_EXCHANGE, BuiltinExchangeType.FANOUT);
        while(i < 5){
            String message = "Hello World - " + i;
            channel.basicPublish(QueueUtil.FANOUT_EXCHANGE, "", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
            i++;
        }
        channel.close();
        connection.close();
    }
}
