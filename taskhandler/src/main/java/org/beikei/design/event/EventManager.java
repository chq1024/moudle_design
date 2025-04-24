package org.beikei.design.event;

import java.util.HashMap;
import java.util.Map;

public class EventManager implements IEventHandler{

    private final Map<EventEnum,IEventHandler> eventHandlerMap = new HashMap<>();

    public void reg2manager(EventEnum eventEnum,IEventHandler eventHandler) {

    }

    @Override
    public void exec(EventEnum eventEnum, EventParameter parameter) {

    }
}
