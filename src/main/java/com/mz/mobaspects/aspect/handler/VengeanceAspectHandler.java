package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.entity.VengeancePearlEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import java.util.List;

public class VengeanceAspectHandler implements IAspectHandler {

    private float radius = 25f;
    private int maxTeleportCount = 4;

    @Override
    public void handleOnDeath(LivingEntity entity, LivingDeathEvent evt) {
        if(evt.isCanceled()){
            return;
        }

        World world = entity.getEntityWorld();
        AxisAlignedBB range = new AxisAlignedBB(entity.getPosX() - radius, entity.getPosY() - radius, entity.getPosZ() - radius,
                entity.getPosX() + radius, entity.getPosY() + radius, entity.getPosZ() + radius);

        int count = 0;
        List<MobEntity> mobEntityList = world.getEntitiesWithinAABB(MonsterEntity.class , range);
        for(MobEntity mob : mobEntityList){
            if(!mob.isAlive() || mob.getUniqueID().equals(entity.getUniqueID())){
                continue;
            }
            count++;
            VengeancePearlEntity pearlEntity = new VengeancePearlEntity(world, entity.getPosX() , entity.getPosY() + 1 , entity.getPosZ(), mob);
            world.addEntity(pearlEntity);
            if(count >= maxTeleportCount){
                break;
            }
        }
    }

    public void setConfig(float radius , int maxTeleportCount){
        this.radius = radius;
        this.maxTeleportCount = maxTeleportCount;
    }

}
