package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.GlassCanonAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class GlassCanonAspect extends AbstractAspect {

    public GlassCanonAspect(){
        super(new GlassCanonAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {

    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.GLASS_CANON;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

    }

}
