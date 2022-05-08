package com.mz.mobaspects.aspect.handler;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;

import java.util.Arrays;
import java.util.List;

public class DebuffAspectHandler implements IAspectHandler {

    private float chance = 0.5f;
    private int durationTicks = 100;

    private final List<Effect> DEBUFF_LIST = Arrays.asList(Effects.NAUSEA , Effects.BLINDNESS , Effects.HUNGER ,
                                                     Effects.SLOWNESS , Effects.UNLUCK , Effects.MINING_FATIGUE);

    @Override
    public void handleOnHit(LivingEntity attacker, LivingEntity victim, float amount, DamageSource damageSource) {
        if(attacker == null || !attacker.isAlive()){
            return;
        }
        if(attacker.getRNG().nextFloat() < chance ){
            return;
        }

        int effectIdx = attacker.getRNG().nextInt(DEBUFF_LIST.size());
        victim.addPotionEffect(new EffectInstance(DEBUFF_LIST.get(effectIdx) , durationTicks));
    }

    public void setConfig(float chance , int durationTicks){
        this.chance = chance;
        this.durationTicks = durationTicks;
    }
}
