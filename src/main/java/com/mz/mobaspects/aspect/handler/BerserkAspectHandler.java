package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.util.EntityAttributeUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.UUID;

public class BerserkAspectHandler implements IAspectHandler {

    private static final UUID SPEED_MODIFIER_IDENTIFIER = UUID.fromString("bf49424e-7d06-44de-b533-ec6830aa1d01");
    private static final UUID DAMAGE_MODIFIER_IDENTIFIER = UUID.fromString("7e1dce9e-e75a-47c1-8f71-28a9dd62a342");

    private float damageIncreasePerLossHealth = 1f;
    private float speedIncreasePerLossHealth = 1f;

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        if (!victim.isAlive() || victim.getHealth() < amount) {
            return;
        }

        float lossHealthPercent = (1 - ((victim.getHealth() - amount) / victim.getMaxHealth()));
        float damageIncrease = lossHealthPercent * damageIncreasePerLossHealth;
        float speedIncrease = lossHealthPercent * speedIncreasePerLossHealth;

        EntityAttributeUtils.ApplyAttributeModifier(victim, Attributes.MOVEMENT_SPEED, SPEED_MODIFIER_IDENTIFIER, speedIncrease, AttributeModifier.Operation.MULTIPLY_TOTAL, "Berserk speed increase");
        EntityAttributeUtils.ApplyAttributeModifier(victim, Attributes.ATTACK_DAMAGE, DAMAGE_MODIFIER_IDENTIFIER, damageIncrease, AttributeModifier.Operation.MULTIPLY_TOTAL, "Berserk damage increase");
    }

    public void setDamageIncreaseValue(float value){
        this.damageIncreasePerLossHealth = value;
    }

    public void setSpeedIncreaseValue(float value){
        this.speedIncreasePerLossHealth = value;
    }
}
