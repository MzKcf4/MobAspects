package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.GhastBuddyAspectHandler;
import com.mz.mobaspects.aspect.handler.OverloadAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class OverloadAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue hitsToExplode;
    private ForgeConfigSpec.IntValue ticksToReduceHitCount;
    private ForgeConfigSpec.IntValue ticksBeforeExplosion;
    private ForgeConfigSpec.DoubleValue explosionRadius;
    private ForgeConfigSpec.BooleanValue countHitsFromPlayerOnly;

    public OverloadAspect(){
        super(new OverloadAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((OverloadAspectHandler)handler).setConfig(hitsToExplode.get() , ticksToReduceHitCount.get() , ticksBeforeExplosion.get() , explosionRadius.get().floatValue() , countHitsFromPlayerOnly.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.OVERLOAD;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Number of hits to take before triggering explosion");
        this.hitsToExplode = builder.defineInRange("hitsToExplode" , 4 , 1 , 20);

        builder.comment("Ticks take to reduce explosion hit counter ( normally 20 ticks = 1 second) ");
        this.ticksToReduceHitCount = builder.defineInRange("ticksToReduceHitCount" , 40 , 10 , 20000);

        builder.comment("Ticks required before explosion ( normally 20 ticks = 1 second) ");
        this.ticksBeforeExplosion = builder.defineInRange("ticksBeforeExplosion" , 100 , 10 , 20000);

        builder.comment("Ticks required before explosion ( normally 20 ticks = 1 second) ");
        this.explosionRadius = builder.defineInRange("explosionRadius" , 5.0D , 0.01D , 128D);

        builder.comment("Increase hit counter from player damages only?");
        this.countHitsFromPlayerOnly = builder.define("countHitsFromPlayerOnly" , true);

    }

}