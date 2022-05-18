package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.CreeperAspectHandler;
import com.mz.mobaspects.aspect.handler.ParasiteAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class ParasiteAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue ticksToAttach;

    public ParasiteAspect(){
        super(new ParasiteAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.PARASITE;
    }

    @Override
    public void applyConfigToHandler() {
        ((ParasiteAspectHandler)handler).setConfig(ticksToAttach.get());
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Ticks required to attach to new host (in ticks , normally 20 ticks = 1 second) ");
        this.ticksToAttach = builder.defineInRange("ticksPerTakeDamage" , 60 , 10 , 300);
    }

}
