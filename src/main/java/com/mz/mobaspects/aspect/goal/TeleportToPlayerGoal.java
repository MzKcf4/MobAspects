package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.util.TeleportUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class TeleportToPlayerGoal extends UseAbilityGoal {

    public TeleportToPlayerGoal(MobEntity mob){
        super(mob);
    }

    @Override
    public boolean canUseAbility() {
        return mobEntity.getAttackTarget() != null;
    }

    @Override
    public void useAbility() {
        LivingEntity target = mobEntity.getAttackTarget();
        if(target == null){
            return;
        }

        if (target.getDistanceSq(this.mobEntity) < 8.0D) {
            TeleportUtils.teleportRandomly(this.mobEntity);
        } else if (target.getDistanceSq(this.mobEntity) >= 8.0D) {
            TeleportUtils.teleportToEntity(this.mobEntity, target);
        }
    }

}
