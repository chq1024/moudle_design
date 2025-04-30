package org.beikei.design.mq.handler;

import lombok.extern.slf4j.Slf4j;
import org.beikei.design.domain.Gamelog;
import org.beikei.design.domain.GamelogTopic;
import org.beikei.design.mq.ConsumerTopic;
import org.beikei.design.mq.MqConsumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.function.Consumer;

@ConsumerTopic(GamelogTopic.LOGIN_TOPIC)
@Slf4j
public class LoginGamelogConsumerHandler extends MqConsumer {

    private final ArrayBlockingQueue<Gamelog> queue = new ArrayBlockingQueue<>(100);

    @Override
    protected ArrayBlockingQueue<Gamelog> queue() {
        return queue;
    }

    @Override
    protected Consumer<Gamelog> handler() {
        return (message)->{
          log.info("LoginGamelog消费:{}",message.getMid());
        };
    }
}
