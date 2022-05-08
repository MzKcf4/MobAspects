package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.util.EntityAttributeUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class SwiftAspectHandler implements IAspectHandler {

    private static final UUID SPEED_MODIFIER_IDENTIFIER = UUID.fromString("e8cd0880-e1fe-4afb-8ec5-72ea415b8a85");
    private static final UUID ATTACK_SPEED_MODIFIER_IDENTIFIER = UUID.fromString("7994d8b7-df9f-4129-973a-638d2f857742");

    private float speedMultiplier = 0.5f;
    private float attackSpeedMultiplier = 0.3f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_IDENTIFIER, speedMultiplier
                , AttributeModifier.Operation.MULTIPLY_TOTAL, "Swift speed increase");

        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER_IDENTIFIER, attackSpeedMultiplier
                , AttributeModifier.Operation.MULTIPLY_TOTAL, "Swift attack speed increase");
    }

    public void setConfig(float speedMultiplier , float attackSpeedMultiplier){
        this.speedMultiplier = speedMultiplier;
        this.attackSpeedMultiplier = attackSpeedMultiplier;
    }
}
