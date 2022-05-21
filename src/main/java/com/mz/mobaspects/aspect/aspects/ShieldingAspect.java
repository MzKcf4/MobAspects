package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.GhastBuddyAspectHandler;
import com.mz.mobaspects.aspect.handler.ShieldingAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class ShieldingAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue cooldown;
    private ForgeConfigSpec.IntValue maxHits;
    private ForgeConfigSpec.BooleanValue instantDestroyByAxe;
    private ForgeConfigSpec.BooleanValue countHitsFromPlayerOnly;

    public ShieldingAspect(){
        super(new ShieldingAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((ShieldingAspectHandler)handler).setConfig( maxHits.get(), cooldown.get() , instantDestroyByAxe.get(), countHitsFromPlayerOnly.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.SHIELDING;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Time to respawn the shield (in ticks , normally 20 ticks = 1 second) ");
        this.cooldown = builder.defineInRange("cooldown" , 160 , 10 , Integer.MAX_VALUE);

        builder.comment("Number of hits to destroy the shield");
        this.maxHits = builder.defineInRange("maxHits" , 3 , 1 , 1000);

        builder.comment("Should axe instant destroy the shield?");
        this.instantDestroyByAxe = builder.define("instantDestroyByAxe" , true);

        builder.comment("Increase hit counter from player damages only?");
        this.countHitsFromPlayerOnly = builder.define("countHitsFromPlayerOnly" , true);
    }

}