package com.mz.mobaspects.entity;

import com.mz.mobaspects.network.NetworkHandler;
import com.mz.mobaspects.network.message.EntityActionEnum;
import com.mz.mobaspects.network.message.EntityActionMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;

public class OverloadCrystalEntity extends AbstractAspectFollowerEntity {

    private int currentHits = 0;
    private int maxHits = 5;
    private int currentTicksToReduceHitCount = 0;
    private int ticksToReduceHitCount = 50;
    private boolean fused = false;
    private int currentTicksBeforeExplosion = 0;
    private int ticksBeforeExplosion = 100;
    private float explosionRadius = 5f;
    private int innerRotation;

    public OverloadCrystalEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.innerRotation = this.rand.nextInt(100000);
    }

    public OverloadCrystalEntity(World worldIn, MobEntity aspectOwner) {
        super(CustomEntityRegister.OVERLOAD_CRYSTAL.get(), worldIn, aspectOwner);
        this.innerRotation = this.rand.nextInt(100000);
    }

    public void setConfig(int maxHits, int ticksToReduceHitCount , int ticksBeforeExplosion , float explosionRadius){
        this.maxHits = maxHits;
        this.ticksToReduceHitCount = ticksToReduceHitCount;
        this.ticksBeforeExplosion = ticksBeforeExplosion;
        this.explosionRadius = explosionRadius;
    }

    @Override
    public void tick() {
        super.tick();
        ++this.innerRotation;
    }

    @Override
    protected void tickServer(){
        tickExplode();
        tickReduceHitCount();
    }

    public void takeHit(){
        if(fused)
            return;

        currentHits++;
        if(currentHits >= maxHits){
            fused = true;
            this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 1.0F);
            sendUpdateMessage(true);
        }
    }

    private void tickExplode(){
        if(!fused)
            return;

        currentTicksBeforeExplosion++;
        if(currentTicksBeforeExplosion >= ticksBeforeExplosion){
            fused = false;
            currentTicksBeforeExplosion = 0;
            currentHits = 0;
            this.world.createExplosion(this, this.getPosX(), this.getPosY(), this.getPosZ(), explosionRadius, Explosion.Mode.NONE);
            sendUpdateMessage(false);
        }
    }

    private void tickReduceHitCount(){
        if(currentHits <= 0 || fused)
            return;

        currentTicksToReduceHitCount++;
        if(currentTicksToReduceHitCount >= ticksToReduceHitCount){
            currentHits--;
            currentTicksToReduceHitCount = 0;
        }
    }

    private void sendUpdateMessage(boolean isFused){
        if(isFused){
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.OVERLOAD_CRYSTAL_FUSE_ON)
            );
        } else {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.OVERLOAD_CRYSTAL_FUSE_OFF)
            );
        }
    }

    public boolean isFused(){
        return this.fused;
    }

    public void setFused(boolean fused){
        this.fused = fused;
    }

    public int getInnerRotation(){
        return this.innerRotation;
    }
}
