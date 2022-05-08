package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.aspect.handler.StoicAspectHandler;
import net.minecraft.entity.MobEntity;

public class StoicTakeDamageGoal extends UseAbilityGoal {

    private float maxDamage = 4.0f;

    public StoicTakeDamageGoal(MobEntity mobEntity, int cooldown, float maxDamage) {
        super(mobEntity , cooldown);
        this.maxDamage = maxDamage;
    }

    @Override
    public boolean shouldExecute() {
        return mobEntity.isAlive() && mobEntity.getPersistentData().getFloat(StoicAspectHandler.NBT_STOIC_DAMAGE_POOL) > 0.0f;
    }

    @Override
    public void useAbility() {
        float damage = mobEntity.getPersistentData().getFloat(StoicAspectHandler.NBT_STOIC_DAMAGE_POOL);
        if(damage > 0.0f){
            float dmgToApply = Math.min(damage , maxDamage);
            mobEntity.attackEntityFrom(StoicAspectHandler.STOIC_DAMAGE_SOURCE, dmgToApply);
            damage -= dmgToApply;
            mobEntity.getPersistentData().putFloat(StoicAspectHandler.NBT_STOIC_DAMAGE_POOL, damage);
        }
    }
}
