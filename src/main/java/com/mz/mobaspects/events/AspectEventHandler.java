package com.mz.mobaspects.events;

import com.mz.mobaspects.aspect.core.AspectManager;
import com.mz.mobaspects.aspect.goal.MoveTowardsSirenEntityGoal;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.config.ServerConfig;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.effects.CustomEffect;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class AspectEventHandler {

    /**
    * The starting point , attach aspect and initialize basic attributes here.
    * */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent evt) {
        Entity entity = evt.getEntity();
        if (!(entity instanceof MonsterEntity) || entity.world.isRemote){
            return;
        }

        ((MonsterEntity) entity).goalSelector.addGoal(0, new MoveTowardsSirenEntityGoal((MonsterEntity) entity, 1.0));

        entity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            List<AspectEnum> aspectList = AspectManager.INSTANCE.rollAspect((MonsterEntity)entity);
            aspectMob.setAspectCodeList(aspectList);

            aspectMob.getAspectCodeList().forEach(code ->
                    AspectManager.INSTANCE.getAspect(code).getHandler().handleOnSpawn((LivingEntity) entity));
        });
    }

    // @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent evt) {}

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent evt) {
        LivingEntity victim = evt.getEntityLiving();
        if(victim.world.isRemote){
            return;
        }

        victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            aspectMob.getAspectCodeList().forEach(code -> {
                AspectManager.INSTANCE.getAspect(code).getHandler().handlePreReceiveHit(victim,evt);
            });
        });
    }

    // @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent evt) {}

    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent evt) {
        LivingEntity victim = evt.getEntityLiving();
        Entity attacker = evt.getSource().getTrueSource();
        if(victim.world.isRemote){
            return;
        }

        // ----- OnReceiveHit ------- //
        victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            aspectMob.getAspectCodeList().forEach(code -> {
                AspectManager.INSTANCE.getAspect(code).getHandler().handleOnReceiveHitServer(attacker,victim,evt.getAmount(),evt.getSource(), evt);
            });
        });

        // -------- OnHit --------- //
        if (!(attacker instanceof LivingEntity) || attacker.world.isRemote){
            return;
        }

        attacker.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            aspectMob.getAspectCodeList().forEach(code -> {
                AspectManager.INSTANCE.getAspect(code).getHandler().handleOnHit((LivingEntity) attacker,victim,evt.getAmount(),evt.getSource());
            });
        });
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent evt) {
        LivingEntity victim = evt.getEntityLiving();
        if (victim.world.isRemote){
            return;
        }

        if(victim.getActivePotionEffect(CustomEffect.UNDYING) != null){
            victim.setHealth(0.1f);

            evt.setCanceled(true);
        }

        victim.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            aspectMob.getAspectCodeList().forEach(code -> {
                AspectManager.INSTANCE.getAspect(code).getHandler().handleOnDeath(victim, evt);
            });
        });
    }

    @SubscribeEvent
    public void onLivingXpDrop(LivingExperienceDropEvent evt) {
        LivingEntity livingEntity = evt.getEntityLiving();
        if (livingEntity.world.isRemote){
            return;
        }

        livingEntity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            float totalMultiplier = ServerConfig.XP_MULTIPLIER_PER_ASPECT.get().floatValue() * aspectMob.getAspectCodeList().size();
            totalMultiplier = totalMultiplier <= 0 ? 1.0f : totalMultiplier;

            int newExp = (int)(evt.getOriginalExperience() * totalMultiplier);
            evt.setDroppedExperience(newExp);
        });
    }
}
