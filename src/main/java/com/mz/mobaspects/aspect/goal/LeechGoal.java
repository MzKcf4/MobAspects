package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;

import java.util.List;

public class LeechGoal extends UseAbilityGoal {

    private float range = 5f;
    private float restorePercentPerUse = 0.1f;

    public LeechGoal(MobEntity mobEntity,int cooldown, float range , float restorePercentPerUse) {
        super(mobEntity , cooldown);
        this.restorePercentPerUse = restorePercentPerUse;
        this.range = range;
    }

    @Override
    public boolean shouldExecute() {
        return mobEntity.getHealth() < mobEntity.getMaxHealth();
    }

    @Override
    public void useAbility() {
        List<MobEntity> nearByMobs = Utils.findNearbyMobs(mobEntity , mobEntity.getEntityWorld() , range);

        if(nearByMobs.size() == 0){
            return;
        }

        float leechTotal = (mobEntity.getMaxHealth()) * restorePercentPerUse;
        float leechPerMob = restorePercentPerUse / nearByMobs.size();

        for(MobEntity mob : nearByMobs){
            mob.attackEntityFrom(DamageSource.GENERIC, leechPerMob);
        }

        mobEntity.heal(leechTotal);
    }
}
