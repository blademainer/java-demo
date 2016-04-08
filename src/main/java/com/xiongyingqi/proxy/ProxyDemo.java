package com.xiongyingqi.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author xiongyingqi
 * @version 2016-04-08 15:00
 */
public class ProxyDemo {
    public static <T> T refer(Class<T> clazz) {
        //        Class<?> proxyClass = Proxy.getProxyClass(ProxyDemo.class.getClassLoader().getParent(), clazz);
        T t = (T) Proxy.newProxyInstance(ProxyDemo.class.getClassLoader(), new Class[] { clazz },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
                        //                        System.out.println(proxy);
                        //                        System.out.println(method);
                        //                        System.out.println(args);
                        System.out.println("start invoke... method: " + method.getName());
                        AServiceImpl aService = AServiceImpl.class.newInstance();
                        aService.doService((String) args[0]);
                        return aService;
                    }
                });
        return t;
    }



    public static void main(String[] args) {
        AService aService = refer(AService.class);
        aService.doService("test");
    }
}

interface AService {
    void doService(String serviceName);
}

class AServiceImpl implements AService {

    @Override
    public void doService(String serviceName) {
        System.out.println("doService... serviceName: " + serviceName);
    }
}


