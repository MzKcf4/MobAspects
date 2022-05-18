package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.entity.CustomTNTEntity;
import com.mz.mobaspects.entity.ParasiteEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class ParasiteAspectHandler implements IAspectHandler {

    private int ticksToAttach = 60;

    @Override
    public void handleOnDeath(LivingEntity entity, LivingDeathEvent evt) {
        if(evt.isCanceled()){
            return;
        }

        ParasiteEntity parasite = new ParasiteEntity(entity.world , Utils.getAspects(entity) , ticksToAttach);
        parasite.setPosition(entity.getPosX() , entity.getPosY() , entity.getPosZ());
        entity.world.addEntity(parasite);
    }

    public void setConfig(int ticksToAttach){
        this.ticksToAttach = ticksToAttach;
    }
}
