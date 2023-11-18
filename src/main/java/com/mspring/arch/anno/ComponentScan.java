package com.mspring.arch.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname ComponentScan
 * @Description Bean扫描注解
 * @Version 1.0.0
 * @Date 11/18/2023 5:56 PM
 * @Created by LIONS7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComponentScan {

    // bean的classpath
    String value() default "";
}
