package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.EnderAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class EndermanAspect extends AbstractAspect {

    public EndermanAspect(){
        super(new EnderAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        // ((EnderAspectHandler)handler).setValues(leechCooldown.get() , leechRange.get().floatValue() , leechHealPercent.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.ENDER_MAN;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

    }

}
