package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.ShootProjectileGoal;
import com.mz.mobaspects.constants.ProjectileTypeEnum;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class GhastBuddyAspectHandler implements IAspectHandler {

    private int cooldown = 100;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new ShootProjectileGoal(mob, cooldown , ProjectileTypeEnum.FIREBALL));

        GhastBuddyEntity buddy = new GhastBuddyEntity(entity.world,  mob);
        Utils.queueFollowerEntitySpawn(entity.world , buddy , (MobEntity) entity);
    }

    public void setConfig(int cooldown){
        this.cooldown = cooldown;
    }

}
