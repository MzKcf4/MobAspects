package com.mz.mobaspects.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class VengeancePearlEntity extends Entity {

    private int summonTime = 60;
    private MobEntity mobToSummon;

    public VengeancePearlEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public VengeancePearlEntity(World worldIn , double x, double y, double z , MobEntity mobToSummon){
        super(EntityType.ENDER_PEARL, worldIn);
        this.setPosition(x , y , z);
        this.mobToSummon = mobToSummon;
        this.setNoGravity(true);
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
        return new SSpawnObjectPacket(this);
    }

    @Override
    public void tick() {
        --summonTime;
        if(summonTime <= 0){
            if(mobToSummon != null && mobToSummon.isAlive()){
                mobToSummon.setPosition(this.getPosX() , this.getPosY() , this.getPosZ());
                mobToSummon.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
            this.remove();
        } else {
            super.tick();
        }
    }
}
