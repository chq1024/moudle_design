package org.beikei.design.mq;

import org.beikei.design.domain.Gamelog;
import org.beikei.design.enums.GamelogEnum;
import org.beikei.design.enums.GamelogParentEnum;
import org.beikei.design.utils.ThreadHelper;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class MqConsumerFactory implements CommandLineRunner {

    private final Map<String,MqConsumer> consumerMap = new HashMap<>();

    public void exec(String topic, Gamelog gamelog) {
        MqConsumer mqConsumer = consumerMap.getOrDefault(topic,null);
        if (mqConsumer == null) {
            throw new RuntimeException("未发现关联主题消费者");
        }
        mqConsumer.add(gamelog);
    }

    public String[] ConsumerTopics() {
        return consumerMap.keySet().toArray(new String[0]);
    }

    @Override
    public void run(String... args) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("org.beikei.design.mq.handler",this.getClass().getClassLoader())
                .addScanners(Scanners.TypesAnnotated));
        Set<Class<?>> handleClasses = reflections.getTypesAnnotatedWith(ConsumerTopic.class);
        for (Class<?> clazz : handleClasses) {
            ConsumerTopic consumer = clazz.getAnnotation(ConsumerTopic.class);
            String topic = consumer.value();
            MqConsumer mqConsumer = (MqConsumer) clazz.getDeclaredConstructor().newInstance();
            consumerMap.put(topic,mqConsumer);
        }
        consumerMap.forEach((k,v)-> ThreadHelper.logThreadGroup().execute(v::exec));
    }
}
