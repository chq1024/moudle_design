package org.beikei.design.client;

import org.beikei.design.enums.GamelogEnum;
import org.beikei.design.mq.MqProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GamelogClient {

    private MqProduct mqProduct;

    @Autowired
    private void setMqProduct(MqProduct mqProduct) {
        this.mqProduct = mqProduct;
    }

    public void send(GamelogEnum gamelogEnum, Map<String, Object> content) {
        mqProduct.send(gamelogEnum, content);
    }

}
