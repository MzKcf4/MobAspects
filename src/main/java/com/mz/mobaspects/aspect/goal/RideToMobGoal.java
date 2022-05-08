package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.world.Difficulty;

import java.util.List;
import java.util.stream.Collectors;

public class RideToMobGoal extends UseAbilityGoal {

    private static final int RANGE = 5;

    public RideToMobGoal(MobEntity mobEntity) {
        super(mobEntity);
    }

    @Override
    public boolean shouldExecute() {
        if (this.mobEntity.getEntityWorld().getDifficulty() == Difficulty.PEACEFUL){
            return false;
        }
        return true;
    }

    @Override
    public boolean canUseAbility() {
        return !mobEntity.isPassenger();
    }

    @Override
    public void useAbility() {
        List<MobEntity> nearByMobs = Utils.findNearbyMobs(mobEntity , mobEntity.getEntityWorld() , RANGE);
        nearByMobs = nearByMobs.stream()
                .filter(entity -> !(entity instanceof HorseEntity) && entity.getRidingEntity() == null)
                .collect(Collectors.toList());

        if(nearByMobs.size() > 0){
            mobEntity.startRiding(nearByMobs.get(0) , true);
        }
    }
}
