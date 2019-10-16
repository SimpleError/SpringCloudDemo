package com.example.rabbitmq.workqueue.demo;

import com.example.rabbitmq.workqueue.ConnectionUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

public class Send {
    private static final String EXCHANGE_NAME = "test_exchange_fanout";

    @Test
    public void testSendWork() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);  //分发
        for(int i = 0; i < 5; i++){
            //发送消息
            String msg = "hello p/s - " + i;
            channel.basicPublish(EXCHANGE_NAME,"", MessageProperties.PERSISTENT_TEXT_PLAIN,msg.getBytes());
            System.out.println("Send " + msg);
        }
        //关闭
        channel.close();
        connection.close();
    }
}
