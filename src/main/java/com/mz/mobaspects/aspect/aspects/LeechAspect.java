package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.LeechingAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

public class LeechAspect extends AbstractAspect {

    private ForgeConfigSpec.IntValue leechCooldown;
    private ForgeConfigSpec.DoubleValue leechRange;
    private ForgeConfigSpec.DoubleValue leechHealPercent;

    public LeechAspect(){
        super(new LeechingAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((LeechingAspectHandler)handler).setValues(leechCooldown.get() , leechRange.get().floatValue() , leechHealPercent.get().floatValue());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.LEECH;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {
        builder.comment("Cooldown of leech ability (in ticks , normally 20 ticks = 1 second) ");
        this.leechCooldown = builder.defineInRange("leechCooldown" , 100 , 1 , Integer.MAX_VALUE);

        builder.comment("Radius of leech");
        this.leechRange = builder.defineInRange("leechRadius" , 5.0D , 0.1D , MobAspectConstants.MOB_DESPAWN_RADIUS);

        builder.comment("Percentage of health restoration when used leech");
        this.leechHealPercent = builder.defineInRange("leechHealPercent" , 0.1D , 0.01D , 1.0D);
    }

}
