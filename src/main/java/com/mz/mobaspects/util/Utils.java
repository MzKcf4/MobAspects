package com.mz.mobaspects.util;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.entity.AbstractAspectFollowerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.concurrent.ThreadTaskExecutor;
import net.minecraft.util.concurrent.TickDelayedTask;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.LogicalSidedProvider;


import java.util.*;

public class Utils {
    private Utils() {}

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
        return mob.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).filter(iAspectMob -> iAspectMob.getAspectCodes().contains(aspect)).isPresent();
    }

    public static Set<AspectEnum> getAspects(LivingEntity entity){
        return entity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).map(IAspectMob::getAspectCodes).orElse(new HashSet<>());
    }

    public static void queueFollowerEntitySpawn(World world , AbstractAspectFollowerEntity followerEntity , MobEntity ownerEntity){
        ThreadTaskExecutor<Runnable> executor = LogicalSidedProvider.WORKQUEUE.get(world.isRemote ? LogicalSide.CLIENT : LogicalSide.SERVER);

        executor.enqueue(new TickDelayedTask(0, () -> {
            world.addEntity(followerEntity);
            ownerEntity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(extraInfo -> extraInfo.addAspectFollower(followerEntity));
        }));
    }

    public static float getHealthPercentage(LivingEntity entity){
        return entity.getHealth() / entity.getMaxHealth();
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
