package com.beikei.design.handler.impl;

import com.beikei.design.event.EventEnum;
import com.beikei.design.event.EventHandlerPoint;
import com.beikei.design.event.EventParameter;
import com.beikei.design.handler.TaskHandler;
import lombok.extern.slf4j.Slf4j;

@EventHandlerPoint(EventEnum.RESTAURANT_UPGRADE)
@Slf4j
public class ResUpTaskHandler implements TaskHandler {

    @Override
    public void exec(EventParameter parameter) {
        log.info("ResUpTaskHandler:exec");
    }
}
