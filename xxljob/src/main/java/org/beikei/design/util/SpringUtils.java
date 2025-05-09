package org.beikei.design.util;

import org.springframework.context.ApplicationContext;

public class SpringUtils {

    private static ApplicationContext context;

    public static void regApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
