package com.mz.mobaspects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class GhastBuddyEntity extends Entity {

    private MobEntity aspectOwner = null;

    public GhastBuddyEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public GhastBuddyEntity(World worldIn) {
        super(CustomEntityRegister.GHAST_BUDDY.get(), worldIn);
    }

    public GhastBuddyEntity(World worldIn, double x, double y, double z , MobEntity aspectOwner) {
        super(CustomEntityRegister.GHAST_BUDDY.get(), worldIn);
        this.setPosition(x, y, z);
        this.aspectOwner = aspectOwner;
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        if(!world.isRemote) {
            if (aspectOwner == null || !aspectOwner.isAlive()) {
                setDead();
            } else {
                this.setPosition(aspectOwner.getPosX(), aspectOwner.getPosY(), aspectOwner.getPosZ());
                this.rotationYaw = aspectOwner.rotationYaw;
            }
        }
        super.tick();
    }
}
