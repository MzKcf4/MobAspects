package com.mz.mobaspects.aspect.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.world.Difficulty;

public abstract class UseAbilityGoal extends Goal {

    protected int abilityNextUse = 120;
    protected int abilityCooldown = 120;
    protected final MobEntity mobEntity;

    public UseAbilityGoal(MobEntity mobEntity) {
        this.mobEntity = mobEntity;
    }

    public UseAbilityGoal(MobEntity mobEntity, int abilityCooldown) {
        this.mobEntity = mobEntity;
        this.abilityCooldown = abilityCooldown;
        this.abilityNextUse = abilityCooldown;
    }

    public abstract void useAbility();

    public boolean canUseAbility(){
        return true;
    }

    @Override
    public boolean shouldExecute() {
        LivingEntity livingentity = this.mobEntity.getAttackTarget();

        if (livingentity == null || !livingentity.isAlive() || this.mobEntity.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL){
            return false;
        }

        return true;
    }

    @Override
    public void startExecuting() {
        this.abilityNextUse = 0;
    }

    @Override
    public void tick() {
        --this.abilityNextUse;
        if(this.abilityNextUse <= 0 && canUseAbility()){
            this.abilityNextUse = this.abilityCooldown;
            useAbility();
        }
    }
}
