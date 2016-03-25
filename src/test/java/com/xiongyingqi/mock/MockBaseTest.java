package com.xiongyingqi.mock;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

/**
 * @author xiongyingqi
 * @version 2016-03-24 14:17
 */
public abstract class MockBaseTest {
    @Before
    public void initMocks(){
        System.out.println("initMocks");
        MockitoAnnotations.initMocks(this);
    }
}
