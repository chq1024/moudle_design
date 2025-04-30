package org.beikei.design;

import org.beikei.design.client.GamelogClient;
import org.beikei.design.enums.GamelogEnum;
import org.beikei.design.enums.GamelogParentEnum;
import org.beikei.design.mq.MqConsumer;
import org.beikei.design.mq.MqConsumerFactory;
import org.beikei.design.utils.DateHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

import static javax.swing.UIManager.put;

@SpringBootApplication
@RestController
public class GamelogMain {
    public static void main(String[] args) {
        SpringApplication.run(GamelogMain.class,args);
    }

    @Autowired
    public GamelogClient gamelogClient;
    @Autowired
    public MqConsumerFactory mqConsumerFactory;
    @GetMapping("/exec")
    public void exec() {
        Random random = new Random();
        String[] topics =  mqConsumerFactory.ConsumerTopics();
        int randomNum = random.nextInt(topics.length);
        String topic = topics[randomNum];
        GamelogParentEnum randomTopicEnum = Arrays.stream(GamelogParentEnum.values()).filter(r->r.getTopic().equals(topic)).findAny().orElse(null);
        List<GamelogEnum> gamelogEnums = Arrays.stream(GamelogEnum.values()).filter(r -> r.getParentEnum().equals(randomTopicEnum)).toList();
        GamelogEnum gamelogEnum = gamelogEnums.get(randomNum);
        gamelogClient.send(gamelogEnum, Map.ofEntries(Map.entry("uname","beikei"),Map.entry("logTime", DateHelper.now())));
    }
}