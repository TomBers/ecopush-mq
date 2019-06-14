package com.ecopush.mq;

import com.ecopush.mq.MessageHandlerTask;
import com.tuya.open.sdk.utils.PulsarConsumerPoolFactory;
import com.tuya.open.sdk.utils.ThreadPoolFactory;
import org.apache.pulsar.client.api.Consumer;

import java.util.function.BiConsumer;

public class ConsumerExample2 {
    public static void main(String[] args) throws Exception {
        PulsarConsumerPoolFactory.getConsumerPool().forEach(new BiConsumer<Integer, Consumer>() {
            @Override
            public void accept(Integer consumerNum, Consumer consumer) {
                ThreadPoolFactory.getCustomThreadPool().submit(new MessageHandlerTask(consumerNum, consumer));
            }
        });
    }
}
