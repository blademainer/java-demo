package com.xiongyingqi.loader;

import org.junit.Assert;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-10-14 18:32
 */
public class ClassLoaderDemoTest {

//    @Test
    public void testInvokeApplicationCotextMethod() throws Exception {
        Class service = ClassLoaderDemo.loadClass("com.xiongyingqi.loader.UserService");
        Object userService = ClassLoaderDemo.invokeApplicationContextMethod("getBean",
                new Class<?>[] { Class.class }, new Object[] { service });
        System.out.println(userService);
        UserService bean = ClassLoaderDemo.getBean(UserService.class);
        System.out.println(bean);
        Assert.assertEquals(userService, bean);
    }
}
