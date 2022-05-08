package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.util.EntityAttributeUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class GlassCanonAspectHandler implements IAspectHandler {

    private static final UUID DAMAGE_MODIFIER_IDENTIFIER = UUID.fromString("31a4db9c-eb7a-48a6-beab-c905090b2339");
    private static final UUID HEALTH_MODIFIER_IDENTIFIER = UUID.fromString("ceacd15c-b1d5-4a2e-8468-93e58bd3527a");
    private static final float HEALTH_PERCENTAGE = -0.5f;
    private static final float DAMAGE_MODIFIER = 0.5f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.MAX_HEALTH , HEALTH_MODIFIER_IDENTIFIER , HEALTH_PERCENTAGE,
                AttributeModifier.Operation.MULTIPLY_TOTAL , "Glass canon health");

        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.ATTACK_DAMAGE , DAMAGE_MODIFIER_IDENTIFIER , DAMAGE_MODIFIER,
                AttributeModifier.Operation.MULTIPLY_TOTAL , "Glass canon damage");
    }
}
