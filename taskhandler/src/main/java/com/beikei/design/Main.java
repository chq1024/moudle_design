package com.beikei.design;

import com.beikei.design.event.*;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@SpringBootApplication
@RestController
public class Main implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .forPackage("com.beikei.design.handler.impl",this.getClass().getClassLoader())
                .addScanners(Scanners.TypesAnnotated)
                .addScanners(Scanners.SubTypes));
        Set<Class<? extends IEventHandler>> handleClasses = reflections.getSubTypesOf(IEventHandler.class);
        for (Class<? extends IEventHandler> clazz : handleClasses) {
            boolean isInterface = clazz.isInterface();
            if (isInterface) {
                continue;
            }
            EventHandlerPoint annotation = clazz.getAnnotation(EventHandlerPoint.class);
            EventEnum eventEnum = annotation.value();
            EventManager.reg2manager(eventEnum,  clazz.getDeclaredConstructor().newInstance());
        }
    }

    @RequestMapping("/exec")
    public void exec() {
        EventManager.exec(EventEnum.RESTAURANT_UPGRADE, EventParameter.builder().param("param").num(1).build());
    }
}