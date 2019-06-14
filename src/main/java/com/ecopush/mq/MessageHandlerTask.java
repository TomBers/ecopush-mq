package com.ecopush.mq;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuya.open.sdk.example.MessageVO;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageHandlerTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerTask.class);

    private Consumer consumer;
    private Integer consumerNum;
    private EcopushServiceImpl ecopushService;
    private String decryptionKey;

    public MessageHandlerTask(Integer consumerNum, Consumer consumer) {
        this.consumerNum = consumerNum;
        this.consumer = consumer;

        this.decryptionKey = System.getenv("ECOPUSH_MQ_DECRYPTION_KEY");

        String url = System.getenv("ECOPUSH_MQ_URL");
        String accessId = System.getenv("ECOPUSH_MQ_ACCESS_ID");
        String accessKey = System.getenv("ECOPUSH_MQ_ACCESS_KEY");

        String postUrl = System.getenv("ECOPUSH_ENDPOINT_URL");
        String postTestUrl = System.getenv("ECOPUSH_ENDPOINT_TEST_URL");

        this.ecopushService = new EcopushServiceImpl(postUrl);

    }

    @Override
    public void run() {
        do {
            try {
                Message message = consumer.receive();
                String data = new String(message.getData());

                MessageWrapper messageWrapper = MessageHandlerTask.deserialise(data);
                String innerMessage = messageWrapper.getMessage(this.decryptionKey);
                System.out.println("Inner message: " + innerMessage);
                this.ecopushService.post(innerMessage);


                consumer.acknowledge(message);

            } catch (Throwable t) {
                System.out.println("error:" + t);
            }
        } while (true);
    }

    public static MessageWrapper deserialise(String data) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, MessageWrapper.class);
    }
}