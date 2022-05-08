package com.mz.mobaspects.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class CustomTNTEntity extends TNTEntity {

    private float explosionRadius;
    private Explosion.Mode mode;

    public CustomTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter, float explosionRadius , boolean canDestroyBlock) {
        super(worldIn, x, y, z, igniter);
        this.explosionRadius = explosionRadius;
        this.mode = canDestroyBlock ? Explosion.Mode.BREAK : Explosion.Mode.NONE;
    }

    protected void explode() {
        this.world.createExplosion(this, this.getPosX(), this.getPosYHeight(0.0625D), this.getPosZ(),
                explosionRadius, mode);
    }

}
