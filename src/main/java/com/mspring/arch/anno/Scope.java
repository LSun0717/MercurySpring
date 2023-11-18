package com.mspring.arch.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname Scope
 * @Description Scope注解
 * @Version 1.0.0
 * @Date 11/18/2023 6:40 PM
 * @Created by LIONS7
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    // Bean的scope
    String value();
}
