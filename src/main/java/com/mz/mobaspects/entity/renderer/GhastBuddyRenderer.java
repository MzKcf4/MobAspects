package com.mz.mobaspects.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.data.ModelsResourceUtil;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GhastBuddyRenderer extends EntityRenderer<GhastBuddyEntity> {
    private static final ResourceLocation GHAST_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast.png");
    private static final ResourceLocation GHAST_SHOOTING_TEXTURES = new ResourceLocation("textures/entity/ghast/ghast_shooting.png");

    private Vector3d center = new Vector3d(2,2,2);

    protected final GhastModel<GhastBuddyEntity> ghastModel = new GhastModel<>();

    public GhastBuddyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public void render(GhastBuddyEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(0.5f,0.5f,0.5f);
        Vector3d rotatedPos = center.rotateYaw((entityIn.ticksExisted + partialTicks)/8);
        matrixStackIn.translate(rotatedPos.x , rotatedPos.y , rotatedPos.z);
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
        ResourceLocation resourceLocation = this.getEntityTexture(entityIn);
        RenderType renderType = this.ghastModel.getRenderType(resourceLocation);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(renderType);
        // Revert upside down
        this.ghastModel.setRotationAngles(entityIn , 0f , 0f ,entityIn.ticksExisted + partialTicks , 0f , 0f);
        this.ghastModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 0.5F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(GhastBuddyEntity entity) {
        // return entity.isAttacking() ? GHAST_SHOOTING_TEXTURES : GHAST_TEXTURES;
        return GHAST_TEXTURES;
    }
}
