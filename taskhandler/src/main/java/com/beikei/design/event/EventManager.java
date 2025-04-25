package com.beikei.design.event;

import com.beikei.design.util.ManualTransaction;
import org.springframework.stereotype.Component;

import java.util.*;

public class EventManager {

    private final static Map<EventEnum, Set<IEventHandler>> eventHandlerMap = new HashMap<>();

    public static void reg2manager(EventEnum eventEnum, IEventHandler eventHandler) {
        eventHandlerMap.compute(eventEnum, (key, val) -> {
            Set<IEventHandler> eventHandlers = Optional.ofNullable(val).orElse(new HashSet<>());
            eventHandlers.add(eventHandler);
            return eventHandlers;
        });
    }

    public static void exec(EventEnum eventEnum, EventParameter parameter) {
        Set<IEventHandler> eventHandlers = eventHandlerMap.getOrDefault(eventEnum, new HashSet<>());
        if (eventHandlers.isEmpty()) {
            return;
        }
        ManualTransaction.doInTransaction(() -> {
            for (IEventHandler eventHandler : eventHandlers) {
                eventHandler.exec(parameter);
            }
        });
    }
}
