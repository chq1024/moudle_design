package org.beikei.design.event;

public interface IEventHandler {

    void exec(EventEnum eventEnum,EventParameter parameter);

}
