package com.mz.mobaspects.aspect.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public interface IAspectHandler {

    default void handleOnHit(LivingEntity attacker, LivingEntity victim ,float amount, DamageSource damageSource) {

    }

    default void handleOnSpawn(LivingEntity entity) {

    }

    default void handleOnDeath(LivingEntity entity, LivingDeathEvent evt) {

    }

    default void handlePreReceiveHit(LivingEntity victim , LivingHurtEvent evt){

    }

    default void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {

    }

    default void handleOnRceiveHitClient(LivingEntity attacker, LivingEntity victim ,float amount, DamageSource damageSource) {

    }
}
