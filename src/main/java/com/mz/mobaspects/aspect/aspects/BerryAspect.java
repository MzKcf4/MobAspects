package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.BerryAspectHandler;
import com.mz.mobaspects.aspect.handler.SirenAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public class BerryAspect extends AbstractAspect {

    private ForgeConfigSpec.DoubleValue berryPlantOnHitChance;
    private ForgeConfigSpec.BooleanValue berryCanPlantOnDeath;
    private ForgeConfigSpec.BooleanValue berryCanDropOnReceiveHit;

    public BerryAspect(){
        super(new BerryAspectHandler());
    }

    @Override
    public void applyConfigToHandler() {
        ((BerryAspectHandler)handler).setConfig(berryPlantOnHitChance.get().floatValue() , berryCanPlantOnDeath.get(), berryCanDropOnReceiveHit.get());
    }

    @Override
    public AspectEnum getCode() {
        return AspectEnum.BERRY;
    }

    @Override
    protected void buildAspectSpecific(ForgeConfigSpec.Builder builder) {

        builder.comment("Chance that will plant a berry bush under the target hit");
        this.berryPlantOnHitChance = builder.defineInRange("berryPlantOnHitChance" , 0.2D , 0.0D , 1.0D);

        builder.comment("Can plant a berry bush when killed");
        this.berryCanPlantOnDeath = builder.define("berryCanPlantOnDeath" , true);

        builder.comment("Can drop berry when receive hit");
        this.berryCanDropOnReceiveHit = builder.define("berryCanPlantOnDeath" , true);

    }

}