package org.beikei.design.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Gamelog {
    private Long id;
    private String mid;
    private Long uid;
    private String type;
    private String subType;
    private String content;
    private Long createTime;
}
