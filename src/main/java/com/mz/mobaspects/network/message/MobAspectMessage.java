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
import java.util.List;
import java.util.function.Supplier;

public class MobAspectMessage {

    private int entityId;
    private int aspectCount;
    private List<AspectEnum> aspectCodeList;

    public MobAspectMessage(){}

    public MobAspectMessage(int entityId , int aspectCount, List<AspectEnum> aspectCodeList){
        this.entityId = entityId;
        this.aspectCount = aspectCount;
        this.aspectCodeList = aspectCodeList;
    }

    public MobAspectMessage(Entity entity , IAspectMob aspectMob){
        this.entityId = entity.getEntityId();
        this.aspectCodeList = aspectMob.getAspectCodeList();
        this.aspectCount = this.aspectCodeList.size();
    }

    public static void encode(MobAspectMessage message , PacketBuffer buffer){
        buffer.writeInt(message.entityId);
        buffer.writeInt(message.aspectCount);
        message.aspectCodeList.forEach(buffer::writeEnumValue);
    }

    public static MobAspectMessage decode(PacketBuffer buffer){
        MobAspectMessage message = new MobAspectMessage();
        message.entityId = buffer.readInt();
        message.aspectCount = buffer.readInt();
        message.aspectCodeList = new ArrayList<>();
        for(int i = 0 ; i < message.aspectCount ; i++){
            message.aspectCodeList.add(buffer.readEnumValue(AspectEnum.class));
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
                        aspectMob.setAspectCodeList(message.aspectCodeList);
                    });
                }
            }
        });

        context.setPacketHandled(true);
    }
}
