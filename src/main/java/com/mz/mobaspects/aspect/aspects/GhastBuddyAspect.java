package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.GhastBuddyAspectHandler;
import com.mz.mobaspects.aspect.handler.LeechingAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class GhastBuddyAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue ghastBuddyFireCooldown;

    public GhastBuddyAspect(){
        super(new GhastBuddyAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((GhastBuddyAspectHandler)handler).setConfig(ghastBuddyFireCooldown.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.GHAST_BUDDY;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Intervals between firing (in ticks , normally 20 ticks = 1 second) ");
        this.ghastBuddyFireCooldown = builder.defineInRange("ghastBuddyFireCooldown" , 120 , 10 , Integer.MAX_VALUE);

    }

}