package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.TeleportToPlayerGoal;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

// Goal runner in GoalSelector :
// 1. Foreach started goals , if !shouldContinueExecuting() , stop
// 2. Foreach stopped goals , if shouldstart() , then startExecuting();
// 3. Foreach started goals , tick()

public class EnderAspectHandler implements IAspectHandler {

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new TeleportToPlayerGoal(mob));
    }
}
