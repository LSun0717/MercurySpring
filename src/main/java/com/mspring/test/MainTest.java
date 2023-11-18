package com.mspring.test;

import com.mspring.arch.context.ApplicationContext;
import com.mspring.test.config.AppConfig;

/**
 * @Classname MainTest
 * @Description 框架测试类
 * @Version 1.0.0
 * @Date 11/18/2023 5:54 PM
 * @Created by LIONS7
 */
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(AppConfig.class);
        Object bean = applicationContext.getBean("userService");
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
        System.out.println(applicationContext.getBean("userService"));
    }
}
