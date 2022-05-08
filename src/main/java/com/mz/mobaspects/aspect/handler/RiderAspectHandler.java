package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.RideToMobGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class RiderAspectHandler implements IAspectHandler {

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new RideToMobGoal(mob));
    }

}
