package com.mz.mobaspects.network.message;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.UndyingTotemAspectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EntityActionMessage {

    private int entityId;
    private EntityActionEnum entityActionEnum;

    public EntityActionMessage(){}

    public EntityActionMessage(int entityId , EntityActionEnum entityActionEnum){
        this.entityId = entityId;
        this.entityActionEnum = entityActionEnum;
    }

    public static void encode(EntityActionMessage message , PacketBuffer buffer){
        buffer.writeInt(message.entityId);
        buffer.writeEnumValue(message.entityActionEnum);
    }

    public static EntityActionMessage decode(PacketBuffer buffer){
        EntityActionMessage message = new EntityActionMessage();
        message.entityId = buffer.readInt();
        message.entityActionEnum = buffer.readEnumValue(EntityActionEnum.class);
        return message;
    }

    public static void handle(EntityActionMessage message , Supplier<NetworkEvent.Context> contextSupplier){
        NetworkEvent.Context context = contextSupplier.get();

        context.enqueueWork( () -> {
            ClientWorld world = Minecraft.getInstance().world;
            if(world != null){
                handleMessageContent(message , world);
            }
        });

        context.setPacketHandled(true);
    }

    private static void handleMessageContent(EntityActionMessage message, ClientWorld world){
        Entity entity = world.getEntityByID(message.entityId);
        if(entity == null){
            return;
        }
        if(EntityActionEnum.UNDYING_TOTEM_ACTIVE == message.entityActionEnum){
            if(!(entity instanceof UndyingTotemAspectEntity)){
                return;
            }
            ((UndyingTotemAspectEntity)entity).setAuraActive(true);
        } else if(EntityActionEnum.UNDYING_TOTEM_DEACTIVE == message.entityActionEnum) {
            if (!(entity instanceof UndyingTotemAspectEntity)) {
                return;
            }
            ((UndyingTotemAspectEntity)entity).setAuraActive(false);
        }
    }
}
