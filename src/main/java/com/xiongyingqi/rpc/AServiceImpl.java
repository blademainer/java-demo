package com.xiongyingqi.rpc;

/**
 * @author xiongyingqi
 * @version 2016-04-08 18:20
 */
public class AServiceImpl implements AService {
    @Override
    public boolean say(String word) {
        System.out.println("AServiceImpl... say: " + word);
        return true;
    }
}
