package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.SwiftAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class SwiftAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue speedMultiplier;
    private ForgeConfigSpec.DoubleValue attackSpeedMultiplier;

    public SwiftAspect(){
        super(new SwiftAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.SWIFT;
    }

    @Override
    public void applyConfigToHandler() {
        ((SwiftAspectHandler)handler).setConfig(speedMultiplier.get().floatValue() - 1,
                attackSpeedMultiplier.get().floatValue() - 1);
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Speed multiplier");
        this.speedMultiplier = builder.defineInRange("speedMultiplier" , 1.5D , 1.0D , 100.0D);

        builder.comment("Attack speed multiplier");
        this.attackSpeedMultiplier = builder.defineInRange("attackSpeedMultiplier" , 1.3D , 1.0D , 100.0D);
    }
}
