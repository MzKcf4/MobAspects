package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface IAspectMob {

    Set<AspectEnum> getAspectCodes();

    void setAspectCodes(Set<AspectEnum> list);

    void addAspectFollower(AbstractAspectFollowerEntity followerEntity);

    Collection<AbstractAspectFollowerEntity> getAspectFollowers();

    List<Integer> getAspectFollowerIds();
}
