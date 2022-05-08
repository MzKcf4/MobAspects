package com.mz.mobaspects.aspect.handler;

import com.mz.mobaspects.util.Utils;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class BerryAspectHandler implements IAspectHandler {

    private float plantOnHitChance = 0.2f;
    private boolean canDropBerryOnReceiveHit = true;
    private boolean canPlantBerryOnDeath = true;

    @Override
    public void handleOnSpawn(LivingEntity entity) {
        MobEntity mob = (MobEntity) entity;
        mob.setHeldItem(Hand.OFF_HAND , new ItemStack(Items.SWEET_BERRIES));
    }

    @Override
    public void handleOnDeath(LivingEntity entity, LivingDeathEvent evt) {
        if(evt.isCanceled()){
            return;
        }

        if(canPlantBerryOnDeath) {
            plantBerryOnEntity(entity);
        }
    }

    @Override
    public void handleOnReceiveHitServer(Entity attacker, LivingEntity victim, float amount, DamageSource damageSource, LivingDamageEvent evt) {
        if(canDropBerryOnReceiveHit) {
            Utils.dropItemIntoWorld(victim.getEntityWorld(), victim.getPosition(), new ItemStack(Items.SWEET_BERRIES, 1 + victim.getRNG().nextInt(4)));
        }
    }

    @Override
    public void handleOnHit(LivingEntity attacker, LivingEntity victim, float amount, DamageSource damageSource) {
        if(victim.getRNG().nextFloat() <= plantOnHitChance){
            plantBerryOnEntity(victim);
        }
    }

    private void plantBerryOnEntity(Entity entity){
        BlockPos posBelow = entity.getPosition().down();
        BlockState bsBelow = entity.getEntityWorld().getBlockState(posBelow);
        if(!bsBelow.getMaterial().isSolid()){
            return;
        }

        BlockPos posCurrent = entity.getPosition();
        BlockState bsCurrent = entity.getEntityWorld().getBlockState(posCurrent);

        if(bsCurrent.getBlock() instanceof AirBlock) {
            entity.getEntityWorld()
                    .setBlockState(posCurrent,
                            Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, 2),
                            2);
        }
    }

    public void setConfig(float plantOnHitChance, boolean canDropBerryOnReceiveHit , boolean canPlantBerryOnDeath){
        this.plantOnHitChance = plantOnHitChance;
        this.canDropBerryOnReceiveHit = canDropBerryOnReceiveHit;
        this.canPlantBerryOnDeath = canPlantBerryOnDeath;
    }

}
