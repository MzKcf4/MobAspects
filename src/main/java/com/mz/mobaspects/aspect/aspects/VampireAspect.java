package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.VampireAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class VampireAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue healPercentPerHit;

    public VampireAspect(){
        super(new VampireAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((VampireAspectHandler)handler).setConfig(healPercentPerHit.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.VAMPIRE;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Percentage of health to heal when hitting entities");
        this.healPercentPerHit = builder.defineInRange("healPercentPerHit" , 0.1D , 0.01D , 1.0D);
    }

}
