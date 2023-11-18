package com.mspring.arch.context;

import com.mspring.arch.anno.Component;
import com.mspring.arch.anno.ComponentScan;
import com.mspring.arch.anno.Scope;
import com.mspring.arch.basic.BeanDefinition;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Classname MercurySpringApplicationContext
 * @Description TODO
 * @Version 1.0.0
 * @Date 11/18/2023 5:53 PM
 * @Created by LIONS7
 */
public class ApplicationContext {
    // 配置类Class
    private Class<?> configClass;
    // SingletonBean Map
    private final ConcurrentHashMap<String, Object> singletonObjs = new ConcurrentHashMap<>();
    // BeanDefinition 对象池
    private final ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public ApplicationContext(Class<?> configClass) {
        this.configClass = configClass;
        scanClassPath(configClass);
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanDefinition);
                singletonObjs.put(beanName, bean);
            }
        }
    }

    /**
     * 扫描指定路径下的Bean
     * @param configClass 配置类Class对象
     */
    private void scanClassPath(Class<?> configClass) {
        ComponentScan componentScan = configClass.getDeclaredAnnotation(ComponentScan.class);
        String path = componentScan.value();
        path = path.replace(".", "//");

        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        URL resource = classLoader.getResource(path);
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                String absPath = f.getAbsolutePath();
                if (absPath.endsWith("class")) {
                    String className = absPath.substring(absPath.indexOf("com"), absPath.indexOf(".class"));
                    className = className.replace("\\", ".");
                    // Bean初始化逻辑
                    try {
                        Class<?> clazz = classLoader.loadClass(className);
                        if (clazz.isAnnotationPresent(Component.class)) {
                            // 生成BeanDefinition
                            Component componentAnno = clazz.getDeclaredAnnotation(Component.class);
                            String beanName = componentAnno.value();
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setClazz(clazz);
                            if (clazz.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnno = clazz.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnno.value());
                            } else {
                                // 默认Scope为singleton
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * 反射创建Bean
     * @param beanDefinition beanDefinition
     * @return Bean实例
     */
    private Object createBean(BeanDefinition beanDefinition) {
        Class<?> clazz = beanDefinition.getClazz();
        try {
            Object bean = clazz.getDeclaredConstructor().newInstance();
            return bean;
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据scope生成对应的
     * @param beanName beanName
     * @return Bean
     */
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            // 根据scope生成对应的Scope的Bean
            if (beanDefinition.getScope().equals("singleton")) {
                Object singletonBean = singletonObjs.get(beanName);
                return singletonBean;
            } else {
                Object prototypeBean = createBean(beanDefinition);
                return prototypeBean;
            }
        } else {
            throw new NullPointerException();
        }
    }
}
