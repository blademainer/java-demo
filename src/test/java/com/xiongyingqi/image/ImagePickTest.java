package com.xiongyingqi.image;

import org.junit.Test;

import java.io.InputStream;

/**
 * @author xiongyingqi
 * @since 16-12-8 下午8:16
 */
public class ImagePickTest {
    @Test
    public void open() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader()
                .getResourceAsStream("images/colorful.jpg");
        System.out.println(resourceAsStream);
        ImagePick.openImage(resourceAsStream);
    }
}
