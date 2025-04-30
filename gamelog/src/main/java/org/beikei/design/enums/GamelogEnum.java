package org.beikei.design.enums;

import lombok.Getter;

@Getter
public enum GamelogEnum {

    LOGIN(GamelogParentEnum.LOGIN,true),
    PAY(GamelogParentEnum.CONSUME,true),
    ITEM_INCR(GamelogParentEnum.RESOURCE,true),
    ITEM_DECR(GamelogParentEnum.RESOURCE,true),
    // -----------可扩展部分 ---------- //
    ASYNC_ACTION(GamelogParentEnum.ACTION,false),
    SYNC_ACTION(GamelogParentEnum.ACTION,true),
    // -----------可扩展部分 ---------- //
    GAME_EXCEPTION(GamelogParentEnum.EXCEPTION,true),
    SYSTEM_EXCEPTION(GamelogParentEnum.EXCEPTION,true),
    ;
    private final Boolean sync;
    private final GamelogParentEnum parentEnum;
    GamelogEnum(GamelogParentEnum parentEnum, Boolean sync) {
        this.parentEnum = parentEnum;
        this.sync = sync;
    }
}
