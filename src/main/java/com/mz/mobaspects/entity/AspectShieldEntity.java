package com.mz.mobaspects.entity;

import com.mz.mobaspects.network.NetworkHandler;
import com.mz.mobaspects.network.message.EntityActionEnum;
import com.mz.mobaspects.network.message.EntityActionMessage;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

public class AspectShieldEntity extends AbstractAspectFollowerEntity {

    private int nextUse = 0;
    private int cooldown = 160;
    private int currentHits = 0;
    private int maxHits = 3;
    private boolean canRender = true;
    private final ItemStack shieldItemStack;


    public AspectShieldEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        shieldItemStack =  new ItemStack(Items.SHIELD, 1);
    }

    public AspectShieldEntity(World worldIn, MobEntity aspectOwner) {
        super(CustomEntityRegister.ASPECT_SHIELD.get(), worldIn, aspectOwner);
        shieldItemStack =  new ItemStack(Items.SHIELD, 1);
    }

    public void setConfig( int maxHits, int cooldown){
        this.cooldown = cooldown;
        this.maxHits = maxHits;
    }

    @Override
    protected void tickServer(){
        tickCooldown();
    }

    public void takeHit(boolean isAxe){
        if(currentHits >= maxHits){
            return;
        }

        currentHits = isAxe ? maxHits : ++currentHits;

        playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
        if(currentHits >= maxHits){
            nextUse = cooldown;
            playSound(SoundEvents.ITEM_SHIELD_BREAK, 1.0F, 1.0F);

            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.SHIELD_BREAK)
            );

        }
    }

    private void tickCooldown(){
        if(currentHits < maxHits){
            return;
        }

        nextUse--;
        if(nextUse <= 0){
            currentHits = 0;
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new EntityActionMessage(this.getEntityId() , EntityActionEnum.SHIELD_ACTIVE));
        }
    }

    public boolean isActive(){
        return currentHits < maxHits;
    }

    public void clientSideShieldBreak(){
        this.canRender = false;

        for(int i = 0 ; i < 5 ; i++){
            addBreakParticle();
        }
    }

    public void clientSideShieldActive(){
        this.canRender = true;
    }

    // Ref : LivingEntity.addItemParticles
    private void addBreakParticle(){
        Vector3d vector3d = new Vector3d(((double)this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
        vector3d = vector3d.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180F));
        vector3d = vector3d.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F));
        double d0 = (double)(-this.rand.nextFloat()) * 0.6D - 0.3D;
        Vector3d vector3d1 = new Vector3d(((double)this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
        vector3d1 = vector3d1.rotatePitch(-this.rotationPitch * ((float)Math.PI / 180F));
        vector3d1 = vector3d1.rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F));
        vector3d1 = vector3d1.add(this.getPosX(), this.getPosYEye(), this.getPosZ());

        this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, shieldItemStack), vector3d1.x, vector3d1.y, vector3d1.z, vector3d.x, vector3d.y + 0.05D, vector3d.z);
    }

    public boolean canRender(){
        return this.canRender;
    }
}
