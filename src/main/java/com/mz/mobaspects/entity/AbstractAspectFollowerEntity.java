package com.mz.mobaspects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class AbstractAspectFollowerEntity extends Entity {

    protected MobEntity aspectOwner;

    public AbstractAspectFollowerEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public AbstractAspectFollowerEntity(EntityType<?> entityTypeIn, World worldIn, MobEntity aspectOwner) {
        super(entityTypeIn, worldIn);
        this.aspectOwner = aspectOwner;
        stickToOwner();
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
        if(world.isRemote){
            super.tick();
            return;
        }

        if (aspectOwner == null || !aspectOwner.isAlive()) {
            setDead();
        } else {
            stickToOwner();
            tickServer();
        }
        super.tick();
    }

    protected void tickServer(){

    }

    protected void stickToOwner(){
        this.setPosition(aspectOwner.getPosX(), aspectOwner.getPosYHeight(0.5D), aspectOwner.getPosZ());
    }

    public MobEntity getAspectOwner(){
        return this.aspectOwner;
    }
}
