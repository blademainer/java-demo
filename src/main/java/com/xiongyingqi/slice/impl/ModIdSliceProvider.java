package com.xiongyingqi.slice.impl;

import com.xiongyingqi.slice.IdSlice;
import com.xiongyingqi.slice.IdSliceProfile;
import com.xiongyingqi.slice.IdSliceProvider;

/**
 * @author xiongyingqi
 * @version 2015-11-27 16:39
 */
public class ModIdSliceProvider implements IdSliceProvider{
    @Override
    public IdSlice newIdSlice(IdSliceProfile profile) {
        return new ModIdSlice(profile);
    }
}
