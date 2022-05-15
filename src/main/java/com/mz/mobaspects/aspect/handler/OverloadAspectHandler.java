package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.entity.OverloadCrystalEntity;
import com.mz.mobaspects.entity.UndyingTotemAspectEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;

import static com.mz.mobaspects.constants.CustomDamageSource.STOIC_DAMAGE_SOURCE;

public class OverloadAspectHandler implements IAspectHandler {

    private int maxHits = 4;
    private int ticksToReduceHitCount = 50;
    private int ticksBeforeExplosion = 100;
    private float explosionRadius = 5f;
    private boolean countHitsFromPlayerOnly = true;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;

        OverloadCrystalEntity followerEntity = new OverloadCrystalEntity(entity.world, mob);
        followerEntity.setConfig(maxHits , ticksToReduceHitCount , ticksBeforeExplosion , explosionRadius);
        Utils.queueFollowerEntitySpawn(entity.world , followerEntity , (MobEntity) entity);
    }

    @Override
    public void handlePreReceiveHit(LivingEntity victim, LivingHurtEvent evt) {
        if(STOIC_DAMAGE_SOURCE.equals(evt.getSource())){
            return;
        }
        if(countHitsFromPlayerOnly){
            if(!(evt.getSource().getTrueSource() instanceof PlayerEntity)){
                return;
            }
        }

        victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(extraInfo -> {
            extraInfo.getAspectFollowers().stream()
                    .filter(followerEntity -> followerEntity instanceof OverloadCrystalEntity)
                    .findFirst()
                    .map(followerEntity -> (OverloadCrystalEntity) followerEntity)
                    .ifPresent(OverloadCrystalEntity::takeHit);
        });
    }

    public void setConfig(int maxHits, int ticksToReduceHitCount , int ticksBeforeExplosion , float explosionRadius , boolean countHitsFromPlayerOnly){
        this.maxHits = maxHits;
        this.ticksToReduceHitCount = ticksToReduceHitCount;
        this.ticksBeforeExplosion = ticksBeforeExplosion;
        this.explosionRadius = explosionRadius;
        this.countHitsFromPlayerOnly = countHitsFromPlayerOnly;
    }
}
