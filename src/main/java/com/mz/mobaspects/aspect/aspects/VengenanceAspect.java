package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.VengeanceAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class VengenanceAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue maxTeleportCount;
    private ForgeConfigSpec.DoubleValue radius;

    public VengenanceAspect(){
        super(new VengeanceAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((VengeanceAspectHandler)handler).setConfig(radius.get().floatValue(), maxTeleportCount.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.VENGEANCE;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Max number of mobs that will be teleported");
        this.maxTeleportCount = builder.defineInRange("maxTeleportCount" , 4 , 1 , 32);

        builder.comment("Radius of teleportation");
        this.radius = builder.defineInRange("radius" , 25.0D , 0.1D , MobAspectConstants.MOB_DESPAWN_RADIUS);
    }

}
