package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.HeavyAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class HeavyAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue speedMultiplier;
    private ForgeConfigSpec.DoubleValue healthMultiplier;
    private ForgeConfigSpec.DoubleValue knockbackAddition;
    private ForgeConfigSpec.DoubleValue knockbackResistanceAddition;

    public HeavyAspect(){
        super(new HeavyAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.HEAVY;
    }

    @Override
    public void applyConfigToHandler() {
        ((HeavyAspectHandler)handler).setConfig(speedMultiplier.get().floatValue() - 1,
                healthMultiplier.get().floatValue() - 1,
                knockbackAddition.get().floatValue(),
                knockbackResistanceAddition.get().floatValue());
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Speed multiplier");
        this.speedMultiplier = builder.defineInRange("speedMultiplier" , 0.5D , 0.0D , 1.0D);

        builder.comment("Health multiplier");
        this.healthMultiplier = builder.defineInRange("healthMultiplier" , 2.0D , 1.0D , 100.0D);

        builder.comment("Knockback addition");
        this.knockbackAddition = builder.defineInRange("knockbackAddition" , 0.3D , 0.0D , 5.0D);

        builder.comment("Knockback resistance addition");
        this.knockbackResistanceAddition = builder.defineInRange("knockbackResistanceAddition" , 0.2D , 0.0D , 1.0D);
    }
}
