package com.xiongyingqi.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-10-14 18:01
 */
public class ClassLoaderDemo {

    public static final String FILE_PATH                      = ClassLoaderDemo.class
            .getClassLoader().getResource("testLib").getFile();
    public static final String DBSERVICE_INTERFACE_CLASS_SIGN = "com.xiongyingqi";

    private static final Logger logger = LoggerFactory
            .getLogger(ClassLoaderDemo.class);

    private static Object         applicationContext;
    private static Class<?>       contextClazz;
    private static URLClassLoader urlClassLoader;

    static {
        try {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            ClassLoader parent = ClassLoader
                    .getSystemClassLoader().getParent();
            logger.info("init... parent is: {}", parent);
            urlClassLoader = new URLClassLoader(listFileUrl(FILE_PATH), parent) {
                @Override
                public Class<?> loadClass(String name) throws ClassNotFoundException {
                    logger.debug("loadClass... name: {}", name);
                    // 如果是dbservice-1.0.0.jar里面的DAO/vo等类，则使用原应用classloader载入的
                    if (name.contains(DBSERVICE_INTERFACE_CLASS_SIGN)) {
                        try {
                            return classLoader.loadClass(name);
                        } catch (ClassNotFoundException e) {
                            return super.loadClass(name);
                        }
                    } else {
                        return super.loadClass(name);
                    }
                }
            };
            Thread.currentThread().setContextClassLoader(urlClassLoader);
            contextClazz = urlClassLoader
                    .loadClass(
                            "org.springframework.context.support.ClassPathXmlApplicationContext");
            Constructor<?> constructor = contextClazz.getConstructor(String.class);
            applicationContext = constructor.newInstance("classpath*:applicationContext.xml");
            logger.debug("new ClassPathXmlApplicationContext... applicationContext: {}",
                    applicationContext);
        } catch (Throwable e) {
            logger.error("", e);
        }
    }

    private static URL[] listFileUrl(String filePath) throws MalformedURLException {
        File file = new File(filePath);
        File[] files = file.listFiles();
        URL[] urls = new URL[files.length];
        for (int i = 0; i < files.length; i++) {
            URL url = new URL("file:" + files[i].getPath());
            urls[i] = url;
        }
        logger.info("urls:{}", Arrays.toString(urls));
        return urls;
    }
    //    private static URL[] listFileUrl(String filePath) throws MalformedURLException {
    //        File file = new File(filePath);
    //        File[] files = file.listFiles();
    //        URL[] urls = new URL[files.length];
    //        for (int i = 0; i < files.length; i++) {
    //            URL url = new URL("file:" + files[i].getPath());
    //            urls[i] = url;
    //        }
    //        logger.info("urls:{}", Arrays.toString(urls));
    //        return urls;
    //    }

    public static Object invokeApplicationContextMethod(String methodName,
                                                        Class<?>[] parameterTypes,
                                                        Object[] parameterValues)
            throws SecurityException, NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Method invokeMethod = contextClazz.getMethod(methodName, parameterTypes);
        Object resp = invokeMethod.invoke(applicationContext, parameterValues);
        return resp;
    }

    public static <T> T getBean(Class<T> beanType)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method invokeMethod = contextClazz.getMethod("getBean", Class.class);
        T instance = (T) invokeMethod.invoke(applicationContext, beanType);
        logger.debug("getBean... beanType: {}, return instance: {}", beanType, instance);
        return instance;
    }

    public static void main(String[] args) throws SecurityException, IllegalArgumentException,
            NoSuchMethodException, IllegalAccessException, InvocationTargetException,
            ClassNotFoundException {
        Class service = urlClassLoader.loadClass("com.xiongyingqi.loader.UserService");
        Object userService = ClassLoaderDemo.invokeApplicationContextMethod("getBean",
                new Class<?>[] { Class.class }, new Object[] { service });
        System.out.println(userService);
    }

    public static Class loadClass(String s) throws ClassNotFoundException {
        Class<?> aClass = urlClassLoader.loadClass(s);
        logger.debug("loadClass... className: {}, return: {}", s, aClass);
        return aClass;
    }
}




