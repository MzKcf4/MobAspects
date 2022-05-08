package com.mz.mobaspects.capability;

import com.mz.mobaspects.aspect.core.AspectMob;
import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.capability.aspect.AspectStorage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;

// Implement ICapabilitySerializable<INBT> , because require store data persistently
public class AspectCapabilityRegisterHelper {

    public static void register() {
        CapabilityManager.INSTANCE.register(IAspectMob.class, new AspectStorage(), AspectMob::new);
        MinecraftForge.EVENT_BUS.register(new AspectCapabilityEventHandler());
    }
}

