package com.mspring.arch.basic;

/**
 * @Classname BeanDefinition
 * @Description 保存Bean的元信息
 * @Version 1.0.0
 * @Date 11/18/2023 6:50 PM
 * @Created by LIONS7
 */
public class BeanDefinition {
    /**
     * Bean的Class对象
     */
    private Class<?> clazz;
    /**
     * Bean的作用域
     */
    private String scope;

    public BeanDefinition(){}

    public BeanDefinition(Class<?> clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
