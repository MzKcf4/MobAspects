package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.entity.CustomTNTEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;

public class StickyBombAspectHandler implements IAspectHandler {

    private float chance = 0.3f;
    private float explosionRadius = 4.0f;
    private boolean explosionDestructive = false;

    public void handleOnHit(LivingEntity attacker , LivingEntity victim , float amount, DamageSource damageSource){
        if(attacker.getRNG().nextFloat() <= chance){
            CustomTNTEntity tnt = new CustomTNTEntity(victim.world , victim.getPosX() , victim.getPosY() + 1 , victim.getPosZ() , null,
                    explosionRadius , explosionDestructive);
            victim.getEntityWorld().addEntity(tnt);
        }
    }

    public void setConfig(float chance , float explosionRadius , boolean explosionDestructive){
        this.chance = chance;
        this.explosionRadius = explosionRadius;
        this.explosionDestructive = explosionDestructive;
    }

}
