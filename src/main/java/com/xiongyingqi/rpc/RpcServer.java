package com.xiongyingqi.rpc;

import com.xiongyingqi.util.Assert;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xiongyingqi
 * @version 2016-04-08 15:13
 */
public class RpcServer {
    private static ServerSocket serverSocket;
    private static ConcurrentHashMap<Class, Object> instanceMap = new ConcurrentHashMap<Class, Object>();
    //    static{
    //        start();
    //    }

    private static void start() throws IOException {
        serverSocket = new ServerSocket(6666);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Socket socket = null;
                    try {
                        socket = serverSocket.accept();
                        final Socket finalSocket = socket;
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    System.out.println("accept socket... " + finalSocket);
                                    accept(finalSocket);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * first, read class type from objectInputStream<br>
     * then, get method name, and get args type of the method, finally gets args
     *
     * @param socket
     * @throws IOException
     */
    private static void accept(Socket socket)
            throws IOException, ClassNotFoundException, NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        while (true) {
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            Class<?> interfaceClass = readClass(objectInputStream);
            Method method = getMethod(objectInputStream, interfaceClass);
            Object[] args = getArgs(objectInputStream);
            Object invoke = invoke(interfaceClass, method, args);

            OutputStream outputStream = socket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(invoke);

//            startInvoke(objectInputStream, objectOutputStream);
            outputStream.flush();
//            outputStream.close();
        }

    }

    private static void startInvoke(ObjectInputStream objectInputStream,
                                    ObjectOutputStream objectOutputStream)
            throws IOException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {

    }

    private static Class<?> readClass(ObjectInputStream inputStream)
            throws IOException, ClassNotFoundException {
        Class<?> clazz = (Class<?>) inputStream.readObject();
        return clazz;
    }

    private static Method getMethod(ObjectInputStream objectInputStream, Class<?> clazz)
            throws IOException, ClassNotFoundException, NoSuchMethodException {
        String methodName = objectInputStream.readUTF();
        Class<?>[] parameterTypes = (Class<?>[]) objectInputStream.readObject();
        System.out.println("getMethod.... methodName: " + methodName + " parameterTypes: " + Arrays.toString(parameterTypes));
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        return method;
    }

    private static Object[] getArgs(ObjectInputStream objectInputStream)
            throws IOException, ClassNotFoundException {
        Object[] args = (Object[]) objectInputStream.readObject();
        return args;
    }

    private static Object invoke(Class<?> clazz, Method method, Object[] args)
            throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = instanceMap.get(clazz);
        Object result = method.invoke(o, args);
//        System.out.println(
//                "class: " + clazz + " method: " + method + " args: " + Arrays.toString(args)
//                        + " result: " + result);
        return result;
    }

    public static void provider(Class<?> clazz, Object object) throws Exception {
        Assert.notNull(clazz);
        Assert.notNull(object);
        System.out.println("provider clazz: " + clazz + "  object: " + object);
        instanceMap.put(clazz, object);
    }

    public static void main(String[] args) {
        try {
            RpcServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            RpcServer.provider(AService.class, new AServiceImpl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
