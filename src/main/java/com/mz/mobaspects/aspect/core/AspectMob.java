package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

// The wrapper dto (capability) class for mobs with aspect.
public class AspectMob implements IAspectMob {
    LivingEntity entity;
    List<AspectEnum> aspectCodeList;

    public AspectMob(){}

    public AspectMob(LivingEntity entity) {
        this.entity = entity;
        this.aspectCodeList = new ArrayList<>();
    }

    @Override
    public List<AspectEnum> getAspectCodeList() {
        return aspectCodeList;
    }

    @Override
    public void setAspectCodeList(List<AspectEnum> list){
        this.aspectCodeList = list;
    }
}
