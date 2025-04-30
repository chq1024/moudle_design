package org.beikei.design.enums;

import lombok.Getter;
import org.beikei.design.domain.GamelogTopic;

@Getter
public enum GamelogParentEnum {

    // 登陆日志
    LOGIN(GamelogTopic.LOGIN_TOPIC),
    // 消费充值日志
    CONSUME(GamelogTopic.CONSUME_TOPIC),
    // 游戏内资源日志
    RESOURCE(GamelogTopic.MAIN_TOPIC),
    // 玩家行为日志
    ACTION(GamelogTopic.MAIN_TOPIC),
    // 异常日志
    EXCEPTION(GamelogTopic.EXCEPTION_TOPIC),
    ;
    private final String topic;
    GamelogParentEnum(String topic) {
        this.topic = topic;
    }
}
