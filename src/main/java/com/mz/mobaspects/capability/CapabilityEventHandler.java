package com.mz.mobaspects.capability;

import com.mz.mobaspects.MobAspects;
import com.mz.mobaspects.aspect.core.AspectManager;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.network.NetworkHandler;
import com.mz.mobaspects.network.message.MobAspectMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

public class CapabilityEventHandler {

    @SubscribeEvent
    public void attachCapabilities(final AttachCapabilitiesEvent<Entity> evt) {
        Entity entity = evt.getObject();

        // The event has a method #addCapability which can be used to attach capabilities to the target object.
        // Instead of adding capabilities themselves to the list, you add capability providers,
        //    which have the chance to return capabilities only from certain sides
        if (AspectManager.INSTANCE.canAttachAspect(entity)) {
            AspectCapabilityProvider provider = new AspectCapabilityProvider((LivingEntity) entity);

            evt.addCapability(new ResourceLocation(MobAspects.MOD_ID, "mobaspects"), provider);

            // When attached entities are removed , all attached providers should invalidate all of their held capability instances.
            evt.addListener(provider::invalidate);
        }
    }

    // By default, capability data is not sent to clients.
    // So mods have to manage their own synchronization code using packets.
    @SubscribeEvent
    public void startTracking(PlayerEvent.StartTracking evt) {
        Entity entity = evt.getTarget();
        PlayerEntity playerEntity = evt.getPlayer();

        if(!(playerEntity instanceof ServerPlayerEntity)){
            return;
        }

        entity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) playerEntity),
                    new MobAspectMessage(entity , aspectMob)
                    );
        });
    }
}
