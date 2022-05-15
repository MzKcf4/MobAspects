package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.entity.UndyingTotemAspectEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class UndyingAuraAspectHandler implements IAspectHandler {

    private int abilityCooldown = 600;
    private int abilityDuration = 120;
    private int effectDuration = 20;
    private float range = 5f;
    private float forceActiveAtHealthPercent = 0.5f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;

        UndyingTotemAspectEntity totem = new UndyingTotemAspectEntity(entity.world, mob);
        totem.setConfig(abilityCooldown , abilityDuration , effectDuration , range);
        Utils.queueFollowerEntitySpawn(entity.world , totem , (MobEntity) entity);
    }

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        if(Utils.getHealthPercentage(victim) <= forceActiveAtHealthPercent){

            victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(extraInfo -> {
                extraInfo.getAspectFollowers().stream()
                        .filter(followerEntity -> followerEntity instanceof UndyingTotemAspectEntity)
                        .findFirst()
                        .map(followerEntity -> (UndyingTotemAspectEntity) followerEntity)
                        .ifPresent(UndyingTotemAspectEntity::forceActivate);
            });
        }
    }

    public void setConfig(int abilityCooldown , int abilityDuration , int effectDuration , float range , float forceActiveAtHealthPercent){
        this.abilityCooldown = abilityCooldown;
        this.abilityDuration = abilityDuration;
        this.effectDuration = effectDuration;
        this.range = range;
        this.forceActiveAtHealthPercent = forceActiveAtHealthPercent;
    }

}
