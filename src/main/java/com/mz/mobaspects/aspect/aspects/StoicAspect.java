package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.StoicAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class StoicAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue ticksPerTakeDamage;
    private ForgeConfigSpec.DoubleValue maxDamageCanTake;

    public StoicAspect(){
        super(new StoicAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((StoicAspectHandler)handler).setConfig(maxDamageCanTake.get().floatValue() , ticksPerTakeDamage.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.STOIC;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Intervals between taking damage from pool (in ticks , normally 20 ticks = 1 second) ");
        this.ticksPerTakeDamage = builder.defineInRange("ticksPerTakeDamage" , 20 , 1 , Integer.MAX_VALUE);

        builder.comment("Maximum damage can take from the pool");
        this.maxDamageCanTake = builder.defineInRange("maxDamageCanTake" , 8.0D , 0.5D , 100D);

    }

}
