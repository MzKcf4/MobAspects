package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Collections;
import java.util.List;

public class SwapGoal extends UseAbilityGoal {

    private float radius = 8f;
    private int cooldown = 100;

    public static int SWAP_RANGE = 8;

    public SwapGoal(MobEntity mobEntity, int cooldown , float radius) {
        super(mobEntity , cooldown);
        this.radius = radius;
    }

    @Override
    public void useAbility() {
        List<MobEntity> nearByMobs = Utils.findNearbyMobs(mobEntity , mobEntity.getEntityWorld() , SWAP_RANGE);

        if(nearByMobs.size() == 0){
            return;
        }

        Collections.shuffle(nearByMobs);
        MobEntity mobToSwap = nearByMobs.get(0);

        Vector3d mobPos = mobEntity.getPositionVec();
        Vector3d otherPos = mobToSwap.getPositionVec();

        mobEntity.setPosition(otherPos.x , otherPos.y , otherPos.z);
        mobToSwap.setPosition(mobPos.x , mobPos.y , mobPos.z);

        mobEntity.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        mobToSwap.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
    }
}
