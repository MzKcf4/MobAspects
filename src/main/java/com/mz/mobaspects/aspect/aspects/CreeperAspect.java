package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.CreeperAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class CreeperAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue explosionRadius;
    private ForgeConfigSpec.BooleanValue explosionDestructive;

    public CreeperAspect(){
        super(new CreeperAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.CREEPER;
    }

    @Override
    public void applyConfigToHandler() {
        ((CreeperAspectHandler)handler).setConfig(explosionRadius.get().floatValue() , explosionDestructive.get());
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Explosion radius");
        this.explosionRadius = builder.defineInRange("explosionRadius" , 4.0D , 0D , Double.MAX_VALUE);

        builder.comment("Explosion destroys blocks?");
        this.explosionDestructive = builder.define("explosionDestructive" , false);
    }

}
