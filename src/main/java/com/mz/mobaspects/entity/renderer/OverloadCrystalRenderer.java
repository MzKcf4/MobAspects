package com.mz.mobaspects.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.entity.OverloadCrystalEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Random;

public class OverloadCrystalRenderer extends EntityRenderer<OverloadCrystalEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
    private static final RenderType ENDER_CRYSTAL_RENDER_TYPE = RenderType.getEntityCutoutNoCull(TEXTURE);
    private static final float field_229047_f_ = (float)Math.sin((Math.PI / 4D));
    private final ModelRenderer field_229048_g_;
    private final ModelRenderer field_229049_h_;

    // beam
    private static final float field_229057_l_ = (float)(Math.sqrt(3.0D) / 2.0D);

    public OverloadCrystalRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.field_229049_h_ = new ModelRenderer(64, 32, 0, 0);
        this.field_229049_h_.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        this.field_229048_g_ = new ModelRenderer(64, 32, 32, 0);
        this.field_229048_g_.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F);
    }

    @Override
    public void render(OverloadCrystalEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.scale(0.5f,0.5f,0.5f);

        float f = func_229051_a_(entityIn, partialTicks);
        float f1 = ((float)entityIn.getInnerRotation() + partialTicks) * 3.0F;
        float newHeight = (1.5F + f / 2.0F);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(ENDER_CRYSTAL_RENDER_TYPE);
        matrixStackIn.push();
            matrixStackIn.scale(2.0F, 2.0F, 2.0F);
            matrixStackIn.translate(0.0D, 0.3D, 0.0D);
            int overlay = OverlayTexture.NO_OVERLAY;

            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
            matrixStackIn.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
            this.field_229049_h_.render(matrixStackIn, ivertexbuilder, packedLightIn, overlay);
            matrixStackIn.scale(0.875F, 0.875F, 0.875F);
            matrixStackIn.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
            this.field_229049_h_.render(matrixStackIn, ivertexbuilder, packedLightIn, overlay);
            matrixStackIn.scale(0.875F, 0.875F, 0.875F);
            matrixStackIn.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(f1));
            this.field_229048_g_.render(matrixStackIn, ivertexbuilder, packedLightIn, overlay);
        matrixStackIn.pop();


        if (entityIn.isFused()) {
            float f5 = ((float)entityIn.ticksExisted + partialTicks) / 200.0F;
            // float f7 = Math.min(f5 > 0.8F ? (f5 - 0.8F) / 0.2F : 0.0F, 1.0F);
            float f7 = Math.min((f5 - 0.8F) / 0.2F, 1.0F);
            Random random = new Random(432L);
            IVertexBuilder ivertexbuilder2 = bufferIn.getBuffer(RenderType.getLightning());
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, newHeight, 0);

            // for(int i = 0; (float)i < (f5 + f5 * f5) / 2.0F * 60.0F; ++i) {
            for(int i = 0; i < 5; ++i) {
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStackIn.rotate(Vector3f.XP.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(random.nextFloat() * 360.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(random.nextFloat() * 360.0F + f5 * 90.0F));
                float f3 = random.nextFloat() * 20.0F + 5.0F + f7 * 10.0F;
                float f4 = random.nextFloat() * 2.0F + 1.0F + f7 * 2.0F;
                Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
                // int j = (int)(255.0F * (1.0F - f7));
                int alpha = 255;
                func_229061_a_(ivertexbuilder2, matrix4f, alpha);
                func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
                func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
                func_229061_a_(ivertexbuilder2, matrix4f, alpha);
                func_229062_b_(ivertexbuilder2, matrix4f, f3, f4);
                func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
                func_229061_a_(ivertexbuilder2, matrix4f, alpha);
                func_229063_c_(ivertexbuilder2, matrix4f, f3, f4);
                func_229060_a_(ivertexbuilder2, matrix4f, f3, f4);
           }

            matrixStackIn.pop();
        }
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(OverloadCrystalEntity entity) {
        return TEXTURE;
    }

    private static void func_229061_a_(IVertexBuilder p_229061_0_, Matrix4f p_229061_1_, int p_229061_2_) {
        p_229061_0_.pos(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
        p_229061_0_.pos(p_229061_1_, 0.0F, 0.0F, 0.0F).color(255, 255, 255, p_229061_2_).endVertex();
    }

    private static void func_229060_a_(IVertexBuilder p_229060_0_, Matrix4f p_229060_1_, float p_229060_2_, float p_229060_3_) {
        p_229060_0_.pos(p_229060_1_, -field_229057_l_ * p_229060_3_, p_229060_2_, -0.5F * p_229060_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229062_b_(IVertexBuilder p_229062_0_, Matrix4f p_229062_1_, float p_229062_2_, float p_229062_3_) {
        p_229062_0_.pos(p_229062_1_, field_229057_l_ * p_229062_3_, p_229062_2_, -0.5F * p_229062_3_).color(255, 0, 255, 0).endVertex();
    }

    private static void func_229063_c_(IVertexBuilder p_229063_0_, Matrix4f p_229063_1_, float p_229063_2_, float p_229063_3_) {
        p_229063_0_.pos(p_229063_1_, 0.0F, p_229063_2_, 1.0F * p_229063_3_).color(255, 0, 255, 0).endVertex();
    }

    public static float func_229051_a_(OverloadCrystalEntity entity, float partialTicks) {
        float f = (float)entity.getInnerRotation() + partialTicks;
        float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
        f1 = (f1 * f1 + f1) * 0.4F;
        return f1 - 1.4F;
    }
}
