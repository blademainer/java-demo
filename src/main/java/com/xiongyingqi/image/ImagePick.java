package com.xiongyingqi.image;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author xiongyingqi
 * @since 16-12-8 下午8:06
 */
public class ImagePick {
    public static void openImage(InputStream inputStream) throws IOException {
        ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
//        Image image = ImageIO.read(inputStream);


        int buffSize = 1024;
        ByteBuffer byteBuffer = ByteBuffer.allocate(buffSize * 1024);
        byte[] buffer = new byte[buffSize];
        for (int size = 0; size >= 0; size = imageInputStream.read(buffer)) {
            byteBuffer.put(buffer);
        }

//        byteBuffer.flip();
        byte[] array = byteBuffer.array();
        long sum = 0;
        for (byte b : array) {
            sum += b;
        }
        System.out.println(sum);
        byte avg = (byte) (sum / array.length);
        System.out.println(avg);
    }
}
