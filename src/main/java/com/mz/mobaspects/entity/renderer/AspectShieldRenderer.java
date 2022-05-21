package com.mz.mobaspects.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import com.mz.mobaspects.entity.AspectShieldEntity;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.util.Utils;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.GhastModel;
import net.minecraft.client.renderer.entity.model.ShieldModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ShieldItem;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.List;

public class AspectShieldRenderer extends EntityRenderer<AspectShieldEntity> {

    private Vector3d center = new Vector3d(0.5,0.3,0.5);
    private ShieldModel modelShield;

    public AspectShieldRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(AspectShieldEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if(this.modelShield == null){
            this.modelShield = new ShieldModel();
        }

        if(entityIn.canRender()) {
            matrixStackIn.push();
            matrixStackIn.scale(.75F, .75F, .75F);
            Vector3d rotatedPos = center.rotateYaw((entityIn.ticksExisted + partialTicks) / 8);
            matrixStackIn.translate(rotatedPos.x, rotatedPos.y, rotatedPos.z);
            matrixStackIn.rotate(this.renderManager.getCameraOrientation());

            RenderMaterial rendermaterial = ModelBakery.LOCATION_SHIELD_NO_PATTERN;
            IVertexBuilder ivertexbuilder = rendermaterial.getSprite().wrapBuffer(ItemRenderer.getEntityGlintVertexBuilder(
                    bufferIn, this.modelShield.getRenderType(rendermaterial.getAtlasLocation()), true, false));

            this.modelShield.func_228293_a_().render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

            matrixStackIn.pop();
        }

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(AspectShieldEntity entity) {
        return null;
    }
}
