package org.beikei.design.mq;

import org.beikei.design.mq.MqConsumer;
import org.beikei.design.domain.Gamelog;
import org.beikei.design.enums.GamelogEnum;
import org.beikei.design.utils.DateHelper;
import org.beikei.design.utils.JsonHelper;
import org.beikei.design.utils.SessionHelper;
import org.beikei.design.utils.ThreadHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * 模拟mq生产者发送消息
 */
@Component
public class MqProduct {

    private MqConsumerFactory consumerFactory;

    @Autowired
    public void setConsumerFactory(MqConsumerFactory consumerFactory) {
        this.consumerFactory = consumerFactory;
    }

    public void send(GamelogEnum gamelogEnum, Map<String, Object> content) {
        Boolean sync = gamelogEnum.getSync();
        if (sync) {
            syncSend(topic(gamelogEnum), toGamelog(gamelogEnum, content));
        } else {
            asyncSend(topic(gamelogEnum), toGamelog(gamelogEnum, content));
        }
    }

    private void asyncSend(String topic, Gamelog gamelog) {
        // 异步发送，不关注是否发送成功至mq服务器，可能出现消息丢失
        ThreadHelper.logThreadGroup().execute(()->{
            consumerFactory.exec(topic, gamelog);
        });
    }

    private void syncSend(String topic, Gamelog gamelog) {
        // 同步发送，保证消息发送至mq服务器，最终一致性
        ThreadHelper.logThreadGroup().execute(()->{
            consumerFactory.exec(topic, gamelog);
        });
    }

    private String topic(GamelogEnum gamelogEnum) {
        return gamelogEnum.getParentEnum().getTopic();
    }

    private Gamelog toGamelog(GamelogEnum gamelogEnum, Map<String, Object> content) {
        return Gamelog.builder().uid(SessionHelper.uid()).mid(UUID.randomUUID().toString()).createTime(DateHelper.now()).type(gamelogEnum.getParentEnum().name()).subType(gamelogEnum.name()).content(JsonHelper.toJson(content)).build();
    }
}
