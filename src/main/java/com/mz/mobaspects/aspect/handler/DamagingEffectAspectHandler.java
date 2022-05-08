package com.mz.mobaspects.aspect.handler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

public class DamagingEffectAspectHandler implements IAspectHandler {

    private float chance = 0.5f;
    private int durationTicks = 100;

    @Override
    public void handleOnHit(LivingEntity attacker, LivingEntity victim, float amount, DamageSource damageSource) {
        if(attacker == null || !attacker.isAlive()){
            return;
        }
        if(attacker.getRNG().nextFloat() < chance ){
            return;
        }

        float rng = attacker.getRNG().nextFloat();
        if(rng < 0.33){
            victim.setFire(durationTicks / 20);
        } else if (rng < 0.66){
            victim.addPotionEffect(new EffectInstance(Effects.POISON , durationTicks));
        } else {
            victim.addPotionEffect(new EffectInstance(Effects.WITHER , durationTicks));
        }
    }

    public void setConfig(float chance , int durationTicks){
        this.chance = chance;
        this.durationTicks = durationTicks;
    }
}
