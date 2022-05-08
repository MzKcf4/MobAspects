package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.BerserkAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class BerserkAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue damageAddPerLossHealthPercent;
    private ForgeConfigSpec.DoubleValue speedAddPerLossHealthPercent;

    public BerserkAspect(){
        super(new BerserkAspectHandler());
    }

    public AspectEnum getCode(){
        return AspectEnum.BERSERK;
    }

    @Override
    public void applyConfigToHandler() {
        ((BerserkAspectHandler)handler).setDamageIncreaseValue(damageAddPerLossHealthPercent.get().floatValue());
        ((BerserkAspectHandler)handler).setSpeedIncreaseValue(speedAddPerLossHealthPercent.get().floatValue());
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Damage multiplier increase per percentage health loss");
        this.damageAddPerLossHealthPercent = builder.defineInRange("damage multiplier" , 1.0D , 0D , Double.MAX_VALUE);

        builder.comment("Speed multiplier increase per percentage health loss");
        this.speedAddPerLossHealthPercent = builder.defineInRange("speed multiplier" , 1.0D , 0D , Double.MAX_VALUE);
    }
}
