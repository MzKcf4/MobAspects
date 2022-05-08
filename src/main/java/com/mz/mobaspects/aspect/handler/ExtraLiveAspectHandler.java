package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class ExtraLiveAspectHandler implements IAspectHandler {

    private float healPercent = 0.5f;

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        if(evt.isCanceled()){
            return;
        }

        if(victim.getHealth() < amount) {
            if (!victim.getPersistentData().getBoolean(MobAspectConstants.ASPECT_NBT_KEY_EXTRA_LIVE_USED)) {
                victim.getPersistentData().putBoolean(MobAspectConstants.ASPECT_NBT_KEY_EXTRA_LIVE_USED, true);
                victim.setHealth(victim.getMaxHealth() * healPercent);
                victim.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP , 1.0f , 1.0f);
                evt.setCanceled(true);
            }
        }
    }

    public void setConfig(float healPercent){
        this.healPercent = healPercent;
    }
}
