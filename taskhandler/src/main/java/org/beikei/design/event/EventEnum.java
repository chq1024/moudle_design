package org.beikei.design.event;

import lombok.Getter;

@Getter
public enum EventEnum {

    RESTAURANT_UPGRADE("res_up_event"),
    ITEM_INCR("item_incr_event"),
    ITEM_DECR("item_decr_event"),
    BATTLE_WIN("battle_win_event"),
    BATTLE_LOSE("battle_lose_event"),
    TASK_COMPLETE("task_complete_event"),
    ;
    private final String name;
    EventEnum(String name) {
        this.name = name;
    }
}
