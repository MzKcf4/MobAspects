package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.StickyBombAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class StickyBombAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue chance;
    private ForgeConfigSpec.DoubleValue explosionRadius;
    private ForgeConfigSpec.BooleanValue explosionDestructive;

    public StickyBombAspect(){
        super(new StickyBombAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.STICKY_BOMB;
    }

    @Override
    public void applyConfigToHandler() {
        ((StickyBombAspectHandler)handler).setConfig(chance.get().floatValue(), explosionRadius.get().floatValue() , explosionDestructive.get());
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Explosion radius");
        this.explosionRadius = builder.defineInRange("explosionRadius" , 4.0D , 0D , Double.MAX_VALUE);

        builder.comment("Explosion destroys blocks?");
        this.explosionDestructive = builder.define("explosionDestructive" , false);

        builder.comment("Chance of applying sticky bomb");
        this.chance = builder.defineInRange("chance" , 0.3D , 0.01D , 1.0D);
    }

}
