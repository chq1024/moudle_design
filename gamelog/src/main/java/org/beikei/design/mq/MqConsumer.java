package org.beikei.design.mq;

import lombok.extern.slf4j.Slf4j;
import org.beikei.design.domain.Gamelog;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 模拟mq消费者消费消息
 */
@Slf4j
public abstract class MqConsumer {

    public void add(Gamelog gamelog) {
        queue().add(gamelog);
    }

    protected abstract ArrayBlockingQueue<Gamelog> queue();

    protected abstract Consumer<Gamelog> handler();

    public void exec() {
        while (true) {
            try {
                Gamelog message = queue().poll(3, TimeUnit.SECONDS);
                if (message == null) {
                    log.warn("{}进入休眠...",Thread.currentThread().getName());
                    Thread.sleep(1000);
                    continue;
                }
                handler().accept(message);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
