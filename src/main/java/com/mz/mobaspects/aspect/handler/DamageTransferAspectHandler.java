package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

public class DamageTransferAspectHandler implements IAspectHandler {

    private float range = 3f;
    private float transferPercent = 0.5f;

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        World world = victim.getEntityWorld();
        if(world.isRemote || amount <= 0){
            return;
        }

        List<MobEntity> mobEntityList = Utils.findNearbyMobs((MobEntity) victim , victim.getEntityWorld() , range);

        float amountToTransfer = amount * transferPercent;

        boolean isDamageTransferred = false;
        for(MobEntity mob : mobEntityList){
            if(!mob.isAlive() || mob.getUniqueID().equals(victim.getUniqueID())){
                continue;
            }

            boolean hasDamageTransfer = mob.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY)
                    .map(IAspectMob::getAspectCodeList)
                    .filter(list -> list.contains(AspectEnum.DAMAGE_TRANSFER))
                    .isPresent();

            if(!hasDamageTransfer){
                mob.attackEntityFrom(damageSource , amountToTransfer);
                isDamageTransferred = true;
                break;
            }
        }

        if(isDamageTransferred){
            evt.setAmount(amount  - amountToTransfer);
        }
    }

    public void setConfig(float range , float transferPercent){
        this.range = range;
        this.transferPercent = transferPercent;
    }
}
