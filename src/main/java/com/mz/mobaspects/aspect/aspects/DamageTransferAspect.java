package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.DamageTransferAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class DamageTransferAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue radius;
    private ForgeConfigSpec.DoubleValue transferPercent;

    public DamageTransferAspect(){
        super(new DamageTransferAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((DamageTransferAspectHandler)handler).setConfig(radius.get().floatValue() , transferPercent.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.DAMAGE_TRANSFER;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Radius of damage transfer");
        this.radius = builder.defineInRange("radius" , 3.0D , 0.1D , MobAspectConstants.MOB_DESPAWN_RADIUS);

        builder.comment("Percentage of damage to be transferred");
        this.transferPercent = builder.defineInRange("transferPercent" , 0.5D , 0.01D , 1.0D);
    }

}
