package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.goal.LeechGoal;
import com.mz.mobaspects.aspect.goal.ShootProjectileGoal;
import com.mz.mobaspects.constants.ProjectileTypeEnum;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.entity.VengeancePearlEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class GhastBuddyAspectHandler implements IAspectHandler {

    private int cooldown = 100;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.goalSelector.addGoal(0 , new ShootProjectileGoal(mob, cooldown , ProjectileTypeEnum.FIREBALL));

        GhastBuddyEntity buddy = new GhastBuddyEntity(entity.world , entity.getPosX() , entity.getEyeHeight()*0.5D, entity.getPosZ() , mob);

        mob.getEntityWorld().addEntity(buddy);
    }

    public void setConfig(int cooldown){
        this.cooldown = cooldown;
    }

}
