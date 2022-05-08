package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MoveTowardsSirenEntityGoal extends Goal {
    private final MobEntity mob;
    private LivingEntity targetEntity;
    private int prevEntityId = 0;
    private final double speed;

    public MoveTowardsSirenEntityGoal(MobEntity creature, double speedIn) {
        this.mob = creature;
        this.speed = speedIn;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        return !this.mob.isPassenger() && hasSirenTarget() && this.mob.getAttackTarget() == null;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        if(this.mob.isPassenger() || this.mob.getAttackTarget() != null || this.mob.getNavigator().noPath()){
            return false;
        }

        checkAndSetTarget();
        return  this.targetEntity != null
                && !this.mob.getNavigator().noPath()
                && this.targetEntity.isAlive();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
        this.targetEntity = null;
        this.prevEntityId = 0;
        mob.getPersistentData().putInt(MobAspectConstants.ASPECT_NBT_KEY_SIREN_MOVE_TO_ID , 0);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        checkAndSetTarget();
    }

    @Override
    public void tick() {
        checkAndSetTarget();
        if(this.targetEntity != null && this.targetEntity.isAlive()){
            this.mob.getNavigator().tryMoveToEntityLiving(this.targetEntity , this.speed);
        }
    }

    private boolean hasSirenTarget(){
        int targetEntityId = mob.getPersistentData().getInt(MobAspectConstants.ASPECT_NBT_KEY_SIREN_MOVE_TO_ID);
        if(targetEntityId == 0){
            return false;
        }

        return mob.getPersistentData().getInt(MobAspectConstants.ASPECT_NBT_KEY_SIREN_MOVE_TO_ID) != 0;
    }

    private void checkAndSetTarget(){
        int targetEntityId = mob.getPersistentData().getInt(MobAspectConstants.ASPECT_NBT_KEY_SIREN_MOVE_TO_ID);
        if(targetEntityId == 0){
            this.targetEntity = null;
            return;
        }
        // No update
        if(prevEntityId == targetEntityId){
            return;
        }

        // new target
        prevEntityId = targetEntityId;
        if(targetEntityId != 0) {
            Entity entity = mob.world.getEntityByID(targetEntityId);
            if(entity instanceof LivingEntity){
                this.targetEntity = (LivingEntity) entity;


                this.mob.getNavigator().tryMoveToEntityLiving(this.targetEntity , this.speed);
            }
        } else {
            this.targetEntity = null;
        }
    }
}
