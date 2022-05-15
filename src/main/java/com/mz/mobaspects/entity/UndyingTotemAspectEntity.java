package com.mz.mobaspects.entity;

import com.mz.mobaspects.effects.CustomEffect;
import com.mz.mobaspects.network.NetworkHandler;
import com.mz.mobaspects.network.message.EntityActionEnum;
import com.mz.mobaspects.network.message.EntityActionMessage;
import com.mz.mobaspects.util.Utils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;

public class UndyingTotemAspectEntity extends AbstractAspectFollowerEntity {

    private int abilityNextUse = 120;
    private int abilityCooldown = 120;

    private int abilityDurationRemain = 0;
    private int abilityDuration = 120;

    private int effectDuration = 20;
    private float range = 5f;

    private boolean isAuraActive = false;
    private boolean forceActivated = false;

    public UndyingTotemAspectEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public UndyingTotemAspectEntity(World worldIn, MobEntity aspectOwner) {
        super(CustomEntityRegister.UNDYING_TOTEM.get(), worldIn, aspectOwner);
    }

    public void setConfig(int abilityCooldown , int abilityDuration , int effectDuration , float range){
        this.abilityCooldown = abilityCooldown;
        this.abilityDuration = abilityDuration;
        this.effectDuration = effectDuration;
        this.range = range;
        this.abilityNextUse = abilityCooldown;
    }

    @Override
    protected void tickServer(){
        checkAndUseAbility();
    }

    public void forceActivate(){
        if(forceActivated){
            return;
        }
        forceActivated = true;
        activateAura();
    }

    public void activateAura(){
        abilityDurationRemain = abilityDuration;
        isAuraActive = true;
        sendUpdateMessage(true);
    }

    private void checkAndUseAbility(){
        if(isAuraActive){
            abilityDurationRemain--;
            applyAuraEffect();
            if(abilityDurationRemain <= 0){
                isAuraActive = false;
                sendUpdateMessage(false);
                // Start cooldown
                abilityNextUse = abilityCooldown;
            }
        } else {
            if(abilityNextUse > 0){
                abilityNextUse--;
                return;
            }
            activateAura();
        }
    }

    private void applyAuraEffect(){
        List<LivingEntity> nearbyLivingEntities = Utils.findNearbyLivingEntities(this , this.getEntityWorld() , range);
        if(nearbyLivingEntities.size() == 0){
            return;
        }
        for(LivingEntity livingEntity : nearbyLivingEntities){
            livingEntity.addPotionEffect(new EffectInstance(CustomEffect.UNDYING, effectDuration));
        }
    }

    private void sendUpdateMessage(boolean isAuraActive){
        if(isAuraActive){
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.UNDYING_TOTEM_ACTIVE)
            );
        } else {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.UNDYING_TOTEM_DEACTIVE)
            );
        }
    }

    public boolean isAuraActive(){
        return isAuraActive;
    }

    public void setAuraActive(boolean isAuraActive){
        this.isAuraActive = isAuraActive;
    }

    public float getRange(){
        return range;
    }
}
