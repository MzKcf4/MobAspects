package com.mz.mobaspects.entity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mz.mobaspects.entity.GhastBuddyEntity;
import com.mz.mobaspects.entity.UndyingTotemAspectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

public class TotemOfUndyingRenderer extends EntityRenderer<UndyingTotemAspectEntity> {
    private final net.minecraft.client.renderer.ItemRenderer itemRenderer;
    private static final ResourceLocation TEXTURE = new ResourceLocation("textures/item/totem_of_undying.png");

    private Vector3d center = new Vector3d(1,1,1);

    private final ItemStack totemItemStack;
    private IBakedModel itemModel;

    public TotemOfUndyingRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.itemRenderer = Minecraft.getInstance().getItemRenderer();
        totemItemStack = new ItemStack(Items.TOTEM_OF_UNDYING, 1);

    }

    @Override
    public void render(UndyingTotemAspectEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if(itemModel == null){
            itemModel = this.itemRenderer.getItemModelWithOverrides(totemItemStack, Minecraft.getInstance().world,null);
        }
        int rotationSpeed = entityIn.isAuraActive() ? 2 : 16;
        float radius = entityIn.isAuraActive() ? entityIn.getRange() : 1;
        center = new Vector3d(radius , 1 , radius);
        matrixStackIn.push();
        Vector3d rotatedPos = center.rotateYaw((entityIn.ticksExisted + partialTicks)/rotationSpeed);
        matrixStackIn.translate(rotatedPos.x , rotatedPos.y , rotatedPos.z);
        matrixStackIn.rotate(this.renderManager.getCameraOrientation());
        this.itemRenderer.renderItem(totemItemStack, ItemCameraTransforms.TransformType.GROUND, false, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY, itemModel);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(UndyingTotemAspectEntity entity) {
        return TEXTURE;
    }
}
