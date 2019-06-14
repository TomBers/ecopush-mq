package com.ecopush.mq;

import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.client.impl.TopicMessageIdImpl;
import com.tuya.open.sdk.mq.MqConsumer;

public class Consumer {

	public static void main(String[] args) throws Exception {


  	String url = System.getenv("ECOPUSH_MQ_URL");
  	String accessId = System.getenv("ECOPUSH_MQ_ACCESS_ID");
  	String accessKey = System.getenv("ECOPUSH_MQ_ACCESS_KEY");
    String decryptionKey = System.getenv("ECOPUSH_MQ_DECRYPTION_KEY");
    String postUrl = System.getenv("ECOPUSH_ENDPOINT_URL");
    String postTestUrl = System.getenv("ECOPUSH_ENDPOINT_TEST_URL");

    EcopushService ecopushService = new EcopushServiceImpl(postUrl);
    EcopushService ecopushTestService = new EcopushServiceImpl(postTestUrl);


		MqConsumer mqConsumer = MqConsumer.build().serviceUrl(url).accessId(accessId).accessKey(accessKey)
				.maxRedeliverCount(3).messageListener(message -> {
          try {
            String data = new String(message.getData());
            System.out.println("Message received:" + data + ",seq="
                + message.getSequenceId() + ",time=" + message.getPublishTime() + ",consumed time="
                + System.currentTimeMillis() + ",partition="
                + ((TopicMessageIdImpl) message.getMessageId()).getTopicPartitionName());

            MessageWrapper messageWrapper = Consumer.deserialise(data);
            String innerMessage = messageWrapper.getMessage(decryptionKey);
            System.out.println("Inner message: " + innerMessage);
            ecopushService.post(innerMessage);
            ecopushTestService.post(innerMessage);
          } catch (Exception e) {
            System.out.println("Error occured while processing message: " + e.getMessage());
            e.printStackTrace();
          }

				});
		mqConsumer.start();
	}

  public static MessageWrapper deserialise(String data) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(data, MessageWrapper.class);
  }

}
