package com.xiongyingqi.rpc;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiongyingqi
 * @version 2016-04-08 17:39
 */
public class RpcClient {
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private InputStream inputStream;
    private OutputStream outputStream;
    private ConcurrentHashMap<Object, Class<?>> instanceAndInterfaceMap = new ConcurrentHashMap<Object, Class<?>>();


    public void start(String host, int port) throws IOException {
        socket = new Socket(host, port);
        System.out.println("connected====" + socket.isConnected());
    }

    public <T> T ref(Class<T> interf) {
        T proxy = createProxy(interf);
//        instanceAndInterfaceMap.put(proxy, interf);
        return proxy;
    }

    @SuppressWarnings("unchecked")
    private <T> T createProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(RpcClient.class.getClassLoader(), new Class[] { clazz },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {
//                        System.out.println("start invoke... method: " + method.getName() + " class: " + clazz);
                        return invokeRemote(clazz, method, args);
                    }
                });
        return (T) o;
    }

    private Object invokeRemote(Class<?> clazz, Method method, Object[] args)
            throws IOException, ClassNotFoundException {
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        System.out.println("invoke remote....");
        objectOutputStream.writeObject(clazz);
        objectOutputStream.writeUTF(method.getName());
        objectOutputStream.writeObject(method.getParameterTypes());
        objectOutputStream.writeObject(args);
        objectOutputStream.flush();
//        objectOutputStream.close();

        inputStream = socket.getInputStream();
        objectInputStream = new ObjectInputStream(inputStream);
        Object result = objectInputStream.readObject();
        return result;
    }

    public static void main(String[] args){
        RpcClient client = new RpcClient();
        try {
            client.start("127.0.0.1", 6666);
            AService aService = client.ref(AService.class);
            for (int i = 0; i < 100; i++) {
                boolean say = aService.say("hello! " + i);
                System.out.println(say);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
