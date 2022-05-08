package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.SirenAspectHandler;
import com.mz.mobaspects.aspect.handler.SwapAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class SirenAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue sirenLureCount;
    private ForgeConfigSpec.DoubleValue sirenLureRadius;
    private ForgeConfigSpec.DoubleValue sirenUseChance;

    public SirenAspect(){
        super(new SirenAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((SirenAspectHandler)handler).setConfig(sirenLureRadius.get().floatValue() , sirenUseChance.get().floatValue(), sirenLureCount.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.SIREN;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Chance that will lure mobs when took hit");
        this.sirenUseChance = builder.defineInRange("sirenUseChance" , 0.5D , 0.01D , 1.0D);

        builder.comment("Amount of mobs that will be attracted");
        this.sirenLureCount = builder.defineInRange("sirenLureCount" , 2 , 1 , 10);

        builder.comment("Range of mobs that will be attracted");
        this.sirenLureRadius = builder.defineInRange("sirenLureRadius" , 32.0D , 16.0D , 64.0D);
    }

}
