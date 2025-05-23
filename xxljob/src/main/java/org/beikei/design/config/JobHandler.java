package org.beikei.design.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface JobHandler {

    String name();

    boolean once() default false;

    String desc() default "";
}
