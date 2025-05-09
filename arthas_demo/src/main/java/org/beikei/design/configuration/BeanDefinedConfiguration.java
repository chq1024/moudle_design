package org.beikei.design.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanDefinedConfiguration {


    @Bean
    public MyBean myBean() {
        return new MyBean("myBean");
    }
}
