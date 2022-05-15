package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

// The wrapper dto (capability) class for mobs with aspect.
public class AspectMob implements IAspectMob {
    private LivingEntity entity;
    private List<AspectEnum> aspectCodeList;
    private HashMap<Integer , AbstractAspectFollowerEntity> followerIdToEntityMap = new HashMap<>();

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

    @Override
    public void addAspectFollower(AbstractAspectFollowerEntity followerEntity) {
        followerIdToEntityMap.put(followerEntity.getEntityId() , followerEntity);
    }

    public Collection<AbstractAspectFollowerEntity> getAspectFollowers(){
        return followerIdToEntityMap.values();
    }

    @Override
    public List<Integer> getAspectFollowerIds() {
        return new ArrayList<>(followerIdToEntityMap.keySet());
    }

}
