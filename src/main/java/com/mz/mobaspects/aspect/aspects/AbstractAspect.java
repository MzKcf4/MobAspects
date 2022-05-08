package com.mz.mobaspects.aspect.aspects;

import com.mz.mobaspects.aspect.handler.IAspectHandler;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraftforge.common.ForgeConfigSpec;

public abstract class AbstractAspect {

    private ForgeConfigSpec.BooleanValue enabled;
    protected final IAspectHandler handler;

    public AbstractAspect(IAspectHandler handler){
        this.handler = handler;
    }

    public IAspectHandler getHandler(){
        return handler;
    }

    public abstract void applyConfigToHandler();

    public abstract AspectEnum getCode();

    public boolean isEnabled(){
        return enabled.get();
    }

    protected abstract void buildAspectSpecific(final ForgeConfigSpec.Builder builder);

    public void buildConfig(final ForgeConfigSpec.Builder builder){
        builder.push(getCode().toString());

        builder.comment("Is this aspect enabled");
        this.enabled = builder.define("enabled" , true);

        buildAspectSpecific(builder);

        builder.pop();
    }


}
