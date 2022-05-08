package com.mz.mobaspects.effects;

import com.mz.mobaspects.MobAspects;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraft.block.Block;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class CustomEffectRegister {

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onEffectsRegistry(final RegistryEvent.Register<Effect> effectRegistryEvent) {
            effectRegistryEvent.getRegistry().registerAll(
                    CustomEffect.UNDYING = new CustomEffect(EffectType.BENEFICIAL , 16777045).setRegistryName(makeResourceName("undying"))
            );
        }

        private static ResourceLocation makeResourceName(String name) {
            return new ResourceLocation(MobAspects.MOD_ID, name);
        }
    }

}
