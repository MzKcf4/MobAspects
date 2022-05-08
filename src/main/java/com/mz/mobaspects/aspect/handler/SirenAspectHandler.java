package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.MoveTowardsSirenEntityGoal;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SirenAspectHandler implements IAspectHandler {

    private float sirenLureRadius = 32f;
    private float sirenUseChance = 0.5f;
    private int sirenLureCount = 2;

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        if(!(victim instanceof MobEntity)){
            return;
        }
        if(victim.getRNG().nextDouble() > sirenUseChance){
            return;
        }
        List<MobEntity> nearByMobs = Utils.findNearbyMobs((MobEntity) victim , victim.getEntityWorld() , sirenLureRadius);

        if(nearByMobs.size() == 0){
            return;
        }

        victim.playSound(SoundEvents.ENTITY_ENDERMAN_SCREAM , 0.9f , 2.0f);
        Collections.shuffle(nearByMobs);

        for(int i = 0 ; i < nearByMobs.size() && i < sirenLureCount; i++){
            MobEntity mob = nearByMobs.get(i);
            mob.getPersistentData().putInt(MobAspectConstants.ASPECT_NBT_KEY_SIREN_MOVE_TO_ID , victim.getEntityId());
        }


    }

    public void setConfig(float sirenLureRadius , float sirenUseChance, int sirenLureCount){
        this.sirenLureRadius = sirenLureRadius;
        this.sirenUseChance = sirenUseChance;
        this.sirenLureCount = sirenLureCount;
    }
}
