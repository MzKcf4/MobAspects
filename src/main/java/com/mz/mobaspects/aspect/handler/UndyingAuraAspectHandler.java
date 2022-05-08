package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.entity.UndyingTotemAspectEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;

public class UndyingAuraAspectHandler implements IAspectHandler {

    private int abilityCooldown = 600;
    private int abilityDuration = 120;
    private int effectDuration = 20;
    private float range = 5f;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;

        UndyingTotemAspectEntity totem = new UndyingTotemAspectEntity(entity.world, mob);
        totem.setConfig(abilityCooldown , abilityDuration , effectDuration , range);
        mob.getEntityWorld().addEntity(totem);
    }

    public void setConfig(int abilityCooldown , int abilityDuration , int effectDuration , float range){
        this.abilityCooldown = abilityCooldown;
        this.abilityDuration = abilityDuration;
        this.effectDuration = effectDuration;
        this.range = range;
    }

}
