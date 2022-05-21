package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.ShootProjectileGoal;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.ProjectileTypeEnum;
import com.mz.mobaspects.entity.AspectShieldEntity;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.entity.OverloadCrystalEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static com.mz.mobaspects.constants.CustomDamageSource.STOIC_DAMAGE_SOURCE;

public class ShieldingAspectHandler implements IAspectHandler {

    private int cooldown = 100;
    private boolean countHitsFromPlayerOnly = true;
    private boolean instantDestroyByAxe = true;
    private int maxHits = 3;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;

        AspectShieldEntity followerEntity = new AspectShieldEntity(entity.world, mob);
        followerEntity.setConfig(maxHits , cooldown);
        Utils.queueFollowerEntitySpawn(entity.world , followerEntity , (MobEntity) entity);


    }

    @Override
    public void handlePreReceiveHit(LivingEntity victim, LivingHurtEvent evt) {
        if(STOIC_DAMAGE_SOURCE.equals(evt.getSource()) || evt.isCanceled()){
            return;
        }

        if(countHitsFromPlayerOnly){
            if(!(evt.getSource().getTrueSource() instanceof PlayerEntity)){
                return;
            }
        }

        boolean isAxe = instantDestroyByAxe && isDamageByAxe(evt.getSource().getImmediateSource());

        victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(extraInfo -> {
            extraInfo.getAspectFollowers().stream()
                    .filter(followerEntity -> followerEntity instanceof AspectShieldEntity)
                    .findFirst()
                    .map(followerEntity -> (AspectShieldEntity) followerEntity)
                    .filter(AspectShieldEntity::isActive)
                    .ifPresent(shield -> {
                        shield.takeHit(isAxe);
                        evt.setCanceled(true);
                    });
        });
    }

    private boolean isDamageByAxe(Entity damageCauser){
        if(!(damageCauser instanceof LivingEntity)){
            return false;
        }
        LivingEntity living = (LivingEntity) damageCauser;
        if(living.getHeldItemMainhand().getItem() instanceof AxeItem){
            return true;
        }
        return false;
    }

    public void setConfig(int maxHits , int cooldown , boolean instantDestroyByAxe , boolean countHitsFromPlayerOnly){
        this.instantDestroyByAxe = instantDestroyByAxe;
        this.cooldown = cooldown;
        this.maxHits = maxHits;
        this.countHitsFromPlayerOnly = countHitsFromPlayerOnly;
    }

}
