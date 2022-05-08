package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.LeechGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class LeechingAspectHandler implements IAspectHandler {

    private int cooldown = 120;
    private float range = 5;
    private float restorePercent;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new LeechGoal(mob, cooldown , range , restorePercent));
    }

    public void setValues(int cooldown, float range , float restorePercent){
        this.cooldown = cooldown;
        this.range = range;
        this.restorePercent = restorePercent;
    }
}
