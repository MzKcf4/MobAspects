package com.mz.mobaspects.events.gui;


import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.util.RaytraceHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Optional;

public class UiEventHandler {

    private static final String TRANSLATION_PREFIX = "aspect.name.";

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if(!Minecraft.isGuiEnabled() || event.getType() != RenderGameOverlayEvent.ElementType.ALL){
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Optional<LivingEntity> livingEntity =
                RaytraceHelper.getLookAtMobAspect(mc, event.getPartialTicks());

        livingEntity.ifPresent(entity -> {
            entity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
                List<AspectEnum> aspectCodeList = aspectMob.getAspectCodeList();
                if(aspectCodeList.size() == 0){
                    return;
                }

                int xOffset = (int)(mc.getMainWindow().getScaledWidth() * 0.05);
                int yOffset = (int)(mc.getMainWindow().getScaledHeight() * 0.1);

                for(AspectEnum aspect : aspectCodeList){
                    mc.fontRenderer.drawTextWithShadow(event.getMatrixStack(),
                            new TranslationTextComponent(TRANSLATION_PREFIX + aspect.getName()),
                            xOffset, yOffset, TextFormatting.RED.getColor());

                    yOffset += 20;
                }
            });
        });
    }
}
