package com.mz.mobaspects.aspect.goal;

import com.mz.mobaspects.constants.ProjectileTypeEnum;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ShootProjectileGoal extends UseAbilityGoal {

    private ProjectileTypeEnum projectileType;

    public ShootProjectileGoal(MobEntity mobEntity, int cooldown , ProjectileTypeEnum projectileType) {
        super(mobEntity , cooldown);
        this.projectileType = projectileType;
    }

    @Override
    public void useAbility() {
        LivingEntity attackTarget = mobEntity.getAttackTarget();
        if(attackTarget == null){
            return;
        }

        double d2 = attackTarget.getPosX() - this.mobEntity.getPosX();
        double d3 = attackTarget.getPosYHeight(0.5D)  - mobEntity.getPosYHeight(0.5D);
        double d4 = attackTarget.getPosZ() - this.mobEntity.getPosZ();

        FireballEntity fireballentity = new FireballEntity(mobEntity.world, mobEntity, d2, d3, d4);
        fireballentity.explosionPower = 0;
        fireballentity.setPosition(this.mobEntity.getPosX(), this.mobEntity.getPosYHeight(0.5D), fireballentity.getPosZ());
        mobEntity.world.addEntity(fireballentity);
    }
}
