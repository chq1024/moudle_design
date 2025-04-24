package org.beikei.design.handler;

import org.beikei.design.event.EventEnum;
import org.beikei.design.event.EventParameter;
import org.beikei.design.event.IEventHandler;

public interface BusinessHandler extends IEventHandler {

    default void exec(EventEnum eventEnum, EventParameter parameter) {
        return;
    }

    void doHandler(EventParameter parameter);
}
