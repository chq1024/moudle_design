package org.beikei.design.configuration;

public class MyBean {

    private String params;
    public MyBean(String param) {
        this.params = param;
    }

    public String print() {
        return params;
    }
}
