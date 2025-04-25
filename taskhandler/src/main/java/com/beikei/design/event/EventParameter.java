package com.beikei.design.event;

import lombok.Builder;

@Builder
public class EventParameter {

    @Builder.Default
    private String param = "";
    @Builder.Default
    private Integer num = 0;
}
