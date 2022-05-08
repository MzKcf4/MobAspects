package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.SwapGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class SwapAspectHandler implements IAspectHandler {

    private int cooldown = 100;
    private float radius = 5.0f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new SwapGoal(mob , cooldown , radius));
    }

    public void setConfig(int cooldown , float radius){
        this.cooldown = cooldown;
        this.radius = radius;
    }
}
