package com.xiongyingqi.slice.impl;

import com.xiongyingqi.slice.IdSlice;
import com.xiongyingqi.slice.IdSliceProfile;

/**
 * @author xiongyingqi
 * @version 2015-11-27 16:41
 */
class ModIdSlice implements IdSlice {
    private IdSliceProfile profile;

    public ModIdSlice(IdSliceProfile profile) {
        this.profile = profile;
    }

    @Override
    public String uuid() {
        return nextId() + "";
    }

    public int nextId(){
        int current = profile.getCurrent();
        int size = profile.getSize();
        int next = current + size;
        profile.setCurrent(next);
        return current;
    }
}
