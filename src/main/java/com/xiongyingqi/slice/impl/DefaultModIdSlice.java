package com.xiongyingqi.slice.impl;

import com.xiongyingqi.slice.IdSlice;
import com.xiongyingqi.slice.IdSliceProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongyingqi
 * @version 2015-11-27 16:49
 */
public class DefaultModIdSlice implements IdSlice {
    private static final Logger logger = LoggerFactory.getLogger(DefaultModIdSlice.class);
    private IdSliceProfile profile;
    private List<IdSlice>  slices;
    private int            currentSliceIndex;

    public DefaultModIdSlice(IdSliceProfile profile) {
        this.profile = profile;
        init(profile);
    }

    private void init(IdSliceProfile profile) {
        int size = profile.getSize();
        slices = new ArrayList<IdSlice>(size);
        for (int i = 0; i < size; i++) {
            IdSliceProfile profileVar = new IdSliceProfile();
            profileVar.setSize(size);
            profileVar.setCurrent(i);
            ModIdSlice slice = new ModIdSlice(profileVar);
            slices.add(slice);
        }

        logger.debug("init... profile: {}, slices: {}", size,
                profile, slices);
    }

    public IdSlice findWhichSliceProduceTheId(String uuid) {
        synchronized (uuid){
            int id = Integer.parseInt(uuid);
            int size = profile.getSize();
            int mod = id % size;
            IdSlice idSlice = slices.get(mod);
            logger.debug("findWhichSliceProduceTheId... uuid: {}, mod: {}, slice: {}", uuid, mod,
                    idSlice);
            return idSlice;
        }
    }

    @Override
    public String uuid() {
        IdSlice slice = getSlice();
        String uuid = slice.uuid();
        logger.debug("uuid... slice: {}, produce uuid: {}", slice, uuid);
        return uuid;
    }

    private IdSlice getSlice() {
        int size = slices.size();
        int index = currentSliceIndex % size;
        IdSlice idSlice = slices.get(index);
        logger.debug("getSlice... size: {}, currentSliceIndex: {}, index: {}, idSlice: {}", size,
                currentSliceIndex, index, idSlice);
        currentSliceIndex++;
        return idSlice;
    }
    
    public static void main(String[] args){
    }
}
