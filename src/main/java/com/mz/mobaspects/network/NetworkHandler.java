package com.mz.mobaspects.network;

import com.mz.mobaspects.MobAspects;
import com.mz.mobaspects.network.message.EntityActionMessage;
import com.mz.mobaspects.network.message.MobAspectMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
    // Should increase the version everytime updating the content.
    public static final String NETWORK_VERSION = "0.2.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(MobAspects.MOD_ID , "network"),
            () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION),
            version -> version.equals(NETWORK_VERSION)
    );

    public static void init(){
        CHANNEL.registerMessage(0 , MobAspectMessage.class , MobAspectMessage::encode , MobAspectMessage::decode , MobAspectMessage::handle);
        CHANNEL.registerMessage(1 , EntityActionMessage.class , EntityActionMessage::encode , EntityActionMessage::decode , EntityActionMessage::handle);
    }
}
