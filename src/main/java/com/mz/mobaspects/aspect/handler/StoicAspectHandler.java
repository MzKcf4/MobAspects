package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.StoicTakeDamageGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class StoicAspectHandler implements IAspectHandler {

    public static String NBT_STOIC_DAMAGE_POOL = "STOIC_DAMAGE_POOL";
    public static final DamageSource STOIC_DAMAGE_SOURCE = new DamageSource("stoicAspect");

    private float maxDamageCanTake = 8.0f;
    private int ticksPerTakeDamage = 20;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new StoicTakeDamageGoal(mob, ticksPerTakeDamage , maxDamageCanTake));
    }

    @Override
    public void handlePreReceiveHit(LivingEntity victim, LivingHurtEvent evt){
        DamageSource source = evt.getSource();

        if(!STOIC_DAMAGE_SOURCE.equals(source)){
            float amount = evt.getAmount();
            float damage = victim.getPersistentData().getFloat(NBT_STOIC_DAMAGE_POOL);
            damage += amount;
            victim.getPersistentData().putFloat(NBT_STOIC_DAMAGE_POOL, damage);
            evt.setAmount(0);
        }
    }

    public void setConfig(float maxDamageCanTake , int ticksPerTakeDamage){
        this.maxDamageCanTake = maxDamageCanTake;
        this.ticksPerTakeDamage = ticksPerTakeDamage;
    }
}
