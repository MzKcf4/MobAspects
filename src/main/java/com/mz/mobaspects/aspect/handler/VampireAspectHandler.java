package com.mz.mobaspects.aspect.handler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class VampireAspectHandler implements IAspectHandler {

    private float healPercent = 0.1f;

    @Override
    public void handleOnHit(LivingEntity attacker, LivingEntity victim, float amount, DamageSource damageSource) {
        if(attacker == null || !attacker.isAlive()){
            return;
        }

        float restore = attacker.getMaxHealth() * healPercent;
        attacker.heal(restore);
    }

    public void setConfig(float healPercent){
        this.healPercent = healPercent;
    }
}
