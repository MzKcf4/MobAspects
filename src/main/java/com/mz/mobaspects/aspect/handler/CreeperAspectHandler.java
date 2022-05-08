package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.entity.CustomTNTEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class CreeperAspectHandler implements IAspectHandler {

    private float explosionRadius = 4.0f;
    private boolean explosionDestructive = false;

    @Override
    public void handleOnDeath(LivingEntity entity, LivingDeathEvent evt) {
        if(evt.isCanceled()){
            return;
        }

        CustomTNTEntity tnt = new CustomTNTEntity(entity.world , entity.getPosX() , entity.getPosY() , entity.getPosZ() , entity, explosionRadius , explosionDestructive);
        entity.getEntityWorld().addEntity(tnt);
    }

    public void setConfig(float explosionRadius , boolean explosionDestructive){
        this.explosionRadius = explosionRadius;
        this.explosionDestructive = explosionDestructive;
    }
}
