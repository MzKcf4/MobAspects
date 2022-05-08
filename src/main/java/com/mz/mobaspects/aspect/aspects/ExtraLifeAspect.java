package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.ExtraLiveAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class ExtraLifeAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue healPercent;

    public ExtraLifeAspect(){
        super(new ExtraLiveAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((ExtraLiveAspectHandler)handler).setConfig(healPercent.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.EXTRA_LIFE;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Percent of heal when used extra life");
        this.healPercent = builder.defineInRange("healPercent" , 0.5D , 0.01D , 1.0D);
    }

}
