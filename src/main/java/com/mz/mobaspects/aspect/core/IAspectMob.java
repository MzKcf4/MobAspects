package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;

import java.util.Collection;
import java.util.List;

public interface IAspectMob {

    List<AspectEnum> getAspectCodeList();

    void setAspectCodeList(List<AspectEnum> list);

    void addAspectFollower(AbstractAspectFollowerEntity followerEntity);

    Collection<AbstractAspectFollowerEntity> getAspectFollowers();

    List<Integer> getAspectFollowerIds();
}
