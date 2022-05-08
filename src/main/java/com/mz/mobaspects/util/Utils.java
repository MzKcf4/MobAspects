package com.mz.mobaspects.util;

import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import java.util.*;

public class Utils {
    private Utils() {};

    public static List<MobEntity> findNearbyMobs(Entity source , World world , float radius){
        AxisAlignedBB range = new AxisAlignedBB(source.getPosX() - radius, source.getPosY() - radius, source.getPosZ() - radius,
                source.getPosX() + radius, source.getPosY() + radius, source.getPosZ() + radius);


        List<MobEntity> mobEntityList = world.getEntitiesWithinAABB(MobEntity.class , range);
        if(source instanceof MobEntity) {
            mobEntityList.remove(source);
        }
        return mobEntityList;
    }

    public static List<LivingEntity> findNearbyLivingEntities(Entity source , World world , float radius){
        AxisAlignedBB range = new AxisAlignedBB(source.getPosX() - radius, source.getPosY() - radius, source.getPosZ() - radius,
                source.getPosX() + radius, source.getPosY() + radius, source.getPosZ() + radius);


        return world.getEntitiesWithinAABB(LivingEntity.class , range);
    }

    public static boolean hasAspect(MobEntity mob , AspectEnum aspect){
        return mob.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).filter(iAspectMob -> iAspectMob.getAspectCodeList().contains(aspect)).isPresent();
    }


    /**
     * Drop an itemstack as entity
     * By rikka0w0
     * https://github.com/rikka0w0/librikka/blob/382f6f79847ae666c22b9c2d6cd591207dfb4303/src/main/java/rikka/librikka/Utils.java#L51
     */
    public static void dropItemIntoWorld(World world, BlockPos pos, ItemStack item) {
        Random rand = new Random();

        if (item != null && item.getCount() > 0) {
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;

            ItemEntity entityItem = new ItemEntity(world,
                    pos.getX() + rx, pos.getY() + ry, pos.getZ() + rz,
                    item.copy());

            if (item.hasTag()) {
                entityItem.getItem().setTag(item.getTag().copy());
            }

            float factor = 0.05F;
            entityItem.setMotion(
                    rand.nextGaussian() * factor,
                    rand.nextGaussian() * factor + 0.2F,
                    rand.nextGaussian() * factor);
            world.addEntity(entityItem);
            item.setCount(0);
        }
    }
}