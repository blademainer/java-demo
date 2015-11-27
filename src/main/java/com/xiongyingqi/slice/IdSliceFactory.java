package com.xiongyingqi.slice;

/**
 * @author xiongyingqi
 * @version 2015-11-27 16:36
 */
public abstract class IdSliceFactory {
    private static IdSliceProvider     provider;

    public static void setProvider(IdSliceProvider provider) {
        IdSliceFactory.provider = provider;
    }

}
