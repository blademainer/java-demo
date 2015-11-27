package com.xiongyingqi.slice.impl;

import com.xiongyingqi.slice.IdSlice;
import com.xiongyingqi.slice.IdSliceProfile;
import org.junit.Test;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author xiongyingqi
 * @version 2015-11-27 17:03
 */
public class DefaultModIdSliceTest {

    @Test
    public void testUuid() throws Exception {
        IdSliceProfile profile = new IdSliceProfile();
        profile.setSize(10);
        DefaultModIdSlice defaultModIdSlice = new DefaultModIdSlice(profile);

        for (int i = 0; i < 100; i++) {
            String uuid = defaultModIdSlice.uuid();
            IdSlice whichSliceProduceTheId =
                    defaultModIdSlice.findWhichSliceProduceTheId(uuid);
            System.out.println(whichSliceProduceTheId);
        }

    }

    public static void main(String[] args) {
        IdSliceProfile profile = new IdSliceProfile();
        profile.setSize(11);
        DefaultModIdSlice defaultModIdSlice = new DefaultModIdSlice(profile);

        ThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(100);
        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() -> {
                String uuid = defaultModIdSlice.uuid();
                IdSlice whichSliceProduceTheId =
                        defaultModIdSlice.findWhichSliceProduceTheId(uuid);
                System.out.println(whichSliceProduceTheId);
            });
            executor.submit(thread);
        }
    }
}
