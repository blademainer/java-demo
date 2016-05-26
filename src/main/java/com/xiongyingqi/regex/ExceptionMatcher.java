package com.xiongyingqi.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiongyingqi on 16-5-26.
 */
public class ExceptionMatcher {
    public static final String  regex   = "Caused\\sby:\\s.*?[\\n\\r]+(\\s*at\\s+.*?[\\n\\r]+)*";
    public static final Pattern PATTERN = Pattern.compile(regex, Pattern.DOTALL);

    static {
    }

    public static void main(String[] args) {
        String test = "Caused by: java.lang.reflect.InvocationTargetException\n"
                + "\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n"
                + "\tat sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)\n"
                + "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)\n"
                + "\tat java.lang.reflect.Method.invoke(Method.java:597)\n"
                + "\tat java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:439)\n"
                + "\tat java.util.concurrent.FutureTask$Sync.innerRunAndReset(FutureTask.java:31\n"
                + "\t... 14 more";
        System.out.println(test);
        Matcher matcher = PATTERN.matcher(test);
        if (matcher.find()) {
            System.out.println("find: \n" + matcher.group());
        }
    }
}
