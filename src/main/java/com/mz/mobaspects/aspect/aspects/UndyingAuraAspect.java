package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.UndyingAuraAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class UndyingAuraAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue undyingAuraCooldown;
    private ForgeConfigSpec.IntValue undyingAuraDuration;
    private ForgeConfigSpec.IntValue undyingAuraEffectDuration;
    private ForgeConfigSpec.DoubleValue undyingAuraRange;

    public UndyingAuraAspect(){
        super(new UndyingAuraAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((UndyingAuraAspectHandler)handler).setConfig(undyingAuraCooldown.get(), undyingAuraDuration.get() , undyingAuraEffectDuration.get() , undyingAuraRange.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.UNDYING_AURA;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Intervals between activation (in ticks , normally 20 ticks = 1 second) ");
        this.undyingAuraCooldown = builder.defineInRange("ghastBuddyFireCooldown" , 600 , 20 , Integer.MAX_VALUE);

        builder.comment("How long the aura exists per activation (in ticks , normally 20 ticks = 1 second) ");
        this.undyingAuraDuration = builder.defineInRange("undyingAuraDuration" , 120 , 10 , Integer.MAX_VALUE);

        builder.comment("Radius of the aura");
        this.undyingAuraRange = builder.defineInRange("undyingAuraRange" , 5f , 1f , 16f);

        builder.comment("Duration of the 'Undying' effect (in ticks , normally 20 ticks = 1 second) ");
        this.undyingAuraEffectDuration = builder.defineInRange("undyingAuraEffectDuration" , 20 , 10 , Integer.MAX_VALUE);

    }

}