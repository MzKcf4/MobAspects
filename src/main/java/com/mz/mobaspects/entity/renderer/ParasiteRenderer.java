package com.mz.mobaspects.entity.renderer;

import com.mz.mobaspects.entity.ParasiteEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.model.EndermiteModel;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ParasiteRenderer extends MobRenderer<ParasiteEntity, EndermiteModel<ParasiteEntity>> {
    private static final ResourceLocation ENDERMITE_TEXTURES = new ResourceLocation("textures/entity/endermite.png");

    public ParasiteRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EndermiteModel<>(), 0.3F);
    }

    protected float getDeathMaxRotation(ParasiteEntity entityLivingBaseIn) {
        return 180.0F;
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(ParasiteEntity entity) {
        return ENDERMITE_TEXTURES;
    }
}