package com.example.rabbitmq.workqueue;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueUtil {
    public final static String SINGLE_QUEUE_NAME = "hello";
    public final static String QUEUE_QUEUE_NAME = "task_queue";

    public final static String DIRECT_EXCHANGE = "direct_exchange";
    public final static String DIRECT_QUEUE = "direct_queue";
    public final static String DIRECT_ROUTING = "direct_routing";

    public final static String FANOUT_EXCHANGE = "fanout_exchange";
    public final static String FANOUT_QUEUE1 = "fanout_queue1";
    public final static String FANOUT_QUEUE2 = "fanout_queue2";
    public final static String FANOUT_ROUTING = "fanout_routing";

    public final static String TOPIC_EXCHANGE = "topic_exchange";
    public final static String TOPIC_QUEUE1 = "topic_queue1";
    public final static String TOPIC_QUEUE2 = "topic_queue2";
    public final static String TOPIC_QUEUE3 = "topic_queue3";
    public final static String TOPIC_QUEUE4 = "topic_queue4";
    public final static String TOPIC_ROUTING = "topic.routing.key";
    public final static String TOPIC_BINDING1 = "topic.*.key";
    public final static String TOPIC_BINDING2 = "topic.#.key";
    public final static String TOPIC_BINDING3 = "topic.#";
    public final static String TOPIC_BINDING4 = "topic.*";

    public static Connection getConnectionFactory() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.43.247");
        factory.setUsername("admin");
        factory.setPassword("admin");
        return factory.newConnection();
    }
}
