package com.mz.mobaspects.network.message;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class MobAspectMessage {

    private int entityId;
    private int aspectCount;
    private Set<AspectEnum> aspectCodes;

    public MobAspectMessage(){}

    public MobAspectMessage(Entity entity , IAspectMob aspectMob){
        this(entity.getEntityId() , aspectMob.getAspectCodes());
    }

    public MobAspectMessage(int entityId , Set<AspectEnum> aspectCodes){
        this.entityId = entityId;
        this.aspectCount = aspectCodes.size();
        this.aspectCodes = aspectCodes;
    }

    public static void encode(MobAspectMessage message , PacketBuffer buffer){
        buffer.writeInt(message.entityId);
        buffer.writeInt(message.aspectCount);
        message.aspectCodes.forEach(buffer::writeEnumValue);
    }

    public static MobAspectMessage decode(PacketBuffer buffer){
        MobAspectMessage message = new MobAspectMessage();
        message.entityId = buffer.readInt();
        message.aspectCount = buffer.readInt();
        message.aspectCodes = new HashSet<>();
        for(int i = 0 ; i < message.aspectCount ; i++){
            message.aspectCodes.add(buffer.readEnumValue(AspectEnum.class));
        }

        return message;
    }

    public static void handle(MobAspectMessage message , Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork( () -> {
            ClientWorld world = Minecraft.getInstance().world;
            if(world != null){
                Entity entity = world.getEntityByID(message.entityId);
                if(entity != null) {
                    entity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
                        aspectMob.setAspectCodes(message.aspectCodes);
                    });
                }
            }
        });

        context.setPacketHandled(true);
    }
}
