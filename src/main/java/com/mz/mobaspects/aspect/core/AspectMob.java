package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;
import net.minecraft.entity.LivingEntity;

import java.util.*;

// The wrapper dto (capability) class for mobs with aspect.
public class AspectMob implements IAspectMob {
    private LivingEntity entity;
    private Set<AspectEnum> aspectCodeList;
    private HashMap<Integer , AbstractAspectFollowerEntity> followerIdToEntityMap = new HashMap<>();

    public AspectMob(){}

    public AspectMob(LivingEntity entity) {
        this.entity = entity;
        this.aspectCodeList = new HashSet<>();
    }


    @Override
    public Set<AspectEnum> getAspectCodes() {
        return aspectCodeList;
    }

    @Override
    public void setAspectCodes(Set<AspectEnum> list){
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
