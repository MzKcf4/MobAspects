package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.RiderAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class RiderAspect extends AbstractAspect {

    public RiderAspect(){
        super(new RiderAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {

    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.RIDER;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

    }

}
