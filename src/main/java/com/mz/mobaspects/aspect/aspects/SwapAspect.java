package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.SwapAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class SwapAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue cooldown;
    private ForgeConfigSpec.DoubleValue radius;

    public SwapAspect(){
        super(new SwapAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((SwapAspectHandler)handler).setConfig(cooldown.get() , radius.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.SWAP;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Cooldown of swap ability (in ticks , normally 20 ticks = 1 second) ");
        this.cooldown = builder.defineInRange("cooldown" , 100 , 1 , Integer.MAX_VALUE);

        builder.comment("Radius of swap");
        this.radius = builder.defineInRange("radius" , 8.0D , 0.1D , MobAspectConstants.MOB_DESPAWN_RADIUS);
    }

}
