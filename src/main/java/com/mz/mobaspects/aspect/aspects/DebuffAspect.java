package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.DebuffAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class DebuffAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue durationTicks;
    private ForgeConfigSpec.DoubleValue chance;

    public DebuffAspect(){
        super(new DebuffAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((DebuffAspectHandler)handler).setConfig(chance.get().floatValue() , durationTicks.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.DEBUFF;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Duration of effect (in ticks , normally 20 ticks = 1 second) ");
        this.durationTicks = builder.defineInRange("durationTicks" , 100 , 1 , Integer.MAX_VALUE);

        builder.comment("Chance of applying effect");
        this.chance = builder.defineInRange("chance" , 0.5D , 0.01D , 1.0D);
    }

}
