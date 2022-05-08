package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.util.EntityAttributeUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class HeavyAspectHandler implements IAspectHandler {

    private static final UUID SPEED_MODIFIER_IDENTIFIER = UUID.fromString("6c24406e-bcb8-46ff-bd1e-6941b0d20051");
    private static final UUID HEALTH_MODIFIER_IDENTIFIER = UUID.fromString("b8cdd1ba-07dd-46cb-8510-b7d22ba130be");

    private static final UUID KNOCKBACK_MODIFIER_IDENTIFIER = UUID.fromString("2fac3cee-da59-4e11-8bd0-985d9e705928");
    private static final UUID KNOCKBACK_RESISTANCE_MODIFIER_IDENTIFIER = UUID.fromString("69252b6a-3087-4e96-b85e-128ad1f82d13");

    private float speedMultiplier = -0.5f;
    private float healthMultiplier = 1f;

    private float knockbackAddition = 0.5f;
    private float knockbackResistanceAddition = 0.2f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_IDENTIFIER, speedMultiplier
                , AttributeModifier.Operation.MULTIPLY_TOTAL, "[Heavy] Speed modifier");
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.MAX_HEALTH, HEALTH_MODIFIER_IDENTIFIER, healthMultiplier
                , AttributeModifier.Operation.MULTIPLY_TOTAL, "[Heavy] Health modifier");
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER_IDENTIFIER, knockbackAddition
                , AttributeModifier.Operation.ADDITION, "[Heavy] Knockback modifier");
        EntityAttributeUtils.ApplyAttributeModifier(entity, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE_MODIFIER_IDENTIFIER, knockbackResistanceAddition
                , AttributeModifier.Operation.ADDITION, "[Heavy] Knockback resistance modifier");

        entity.setHealth(entity.getMaxHealth());
    }

    public void setConfig(float speedMultiplier , float healthMultiplier , float knockbackAddition , float knockbackResistanceAddition){
        this.speedMultiplier = speedMultiplier;
        this.healthMultiplier = healthMultiplier;
        this.knockbackAddition = knockbackAddition;
        this.knockbackResistanceAddition = knockbackResistanceAddition;
    }
}
