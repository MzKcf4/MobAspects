package com.mz.mobaspects.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class TeleportUtils {

    public static boolean teleportToEntity(LivingEntity source, Entity target) {
        Vector3d vector3d = new Vector3d(source.getPosX() - target.getPosX(), source.getPosYHeight(0.5D) - target.getPosYEye(), source.getPosZ() - target.getPosZ());
        vector3d = vector3d.normalize();
        double d0 = 16.0D;
        double d1 = source.getPosX() + (source.getRNG().nextDouble() - 0.5D) * 8.0D - vector3d.x * 16.0D;
        double d2 = source.getPosY() + (double)(source.getRNG().nextInt(16) - 8) - vector3d.y * 16.0D;
        double d3 = source.getPosZ() + (source.getRNG().nextDouble() - 0.5D) * 8.0D - vector3d.z * 16.0D;
        return teleportTo(source, d1, d2, d3);
    }

    public static boolean teleportRandomly(LivingEntity source) {
        if (!source.world.isRemote() && source.isAlive()) {
            double d0 = source.getPosX() + (source.getRNG().nextDouble() - 0.5D) * 4.0D;
            double d1 = source.getPosY() + (double)(source.getRNG().nextInt(16) - 4);
            double d2 = source.getPosZ() + (source.getRNG().nextDouble() - 0.5D) * 4.0D;
            return teleportTo(source, d0, d1, d2);
        } else {
            return false;
        }
    }

    public static boolean teleportTo(LivingEntity source, double x, double y, double z) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(x, y, z);

        while(blockpos$mutable.getY() > 0 && !source.world.getBlockState(blockpos$mutable).getMaterial().blocksMovement()) {
            blockpos$mutable.move(Direction.DOWN);
        }

        BlockState blockstate = source.world.getBlockState(blockpos$mutable);
        boolean flag = blockstate.getMaterial().blocksMovement();
        boolean flag1 = blockstate.getFluidState().isTagged(FluidTags.WATER);
        if (flag && !flag1) {
            net.minecraftforge.event.entity.living.EntityTeleportEvent.EnderEntity event = net.minecraftforge.event.ForgeEventFactory.onEnderTeleport(source, x, y, z);
            if (event.isCanceled()) return false;
            boolean flag2 = source.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ(), true);
            if (flag2 && !source.isSilent()) {
                source.world.playSound((PlayerEntity)null, source.prevPosX, source.prevPosY, source.prevPosZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, source.getSoundCategory(), 1.0F, 1.0F);
                source.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }

            return flag2;
        } else {
            return false;
        }
    }
}
