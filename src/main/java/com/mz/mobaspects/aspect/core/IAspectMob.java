package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.constants.AspectEnum;

import java.util.List;

public interface IAspectMob {

    // IntArrayList getAspectCodeList();

    // void setAspectCodeList(IntArrayList list);

    List<AspectEnum> getAspectCodeList();

    void setAspectCodeList(List<AspectEnum> list);
}
