package com.mz.mobaspects.util;

import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Optional;

public class RaytraceHelper {
    // Reference : https://github.com/TheIllusiveC4/Champions
    // Also refer to : GameRenderer.getMouseOver
    public static Optional<LivingEntity> getLookAtMobAspect(Minecraft mc, float partialTicks) {
        Entity entity = mc.getRenderViewEntity();
        if(entity == null || mc.world == null){
            return Optional.empty();
        }

        mc.getProfiler().startSection("getLookAtMobAspect_mobaspect");
        double d0 = 16;
        RayTraceResult rayTraceResult = entity.pick(d0, partialTicks, false);
        Vector3d vec3d = entity.getEyePosition(partialTicks);
        double d1 = rayTraceResult.getHitVec().squareDistanceTo(vec3d);
        Vector3d vec3d1 = entity.getLook(1.0F);
        Vector3d vec3d2 = vec3d.add(vec3d1.x * d0, vec3d1.y * d0, vec3d1.z * d0);
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(vec3d1.scale(d0))
                .grow(1.0D, 1.0D, 1.0D);
        EntityRayTraceResult entityraytraceresult = ProjectileHelper
                .rayTraceEntities(entity, vec3d, vec3d2, axisalignedbb,
                        (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith(), d1);

        if (entityraytraceresult != null) {
            Entity entity1 = entityraytraceresult.getEntity();

            if (entity1 instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity1;

                return livingEntity.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).isPresent() ?
                        Optional.of(livingEntity) : Optional.empty();
            }
        }
        mc.getProfiler().endSection();

        return Optional.empty();
    }
}
