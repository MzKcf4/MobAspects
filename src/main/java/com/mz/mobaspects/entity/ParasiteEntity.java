package com.mz.mobaspects.entity;

import com.mz.mobaspects.aspect.core.AspectManager;
import com.mz.mobaspects.capability.aspect.AspectCapabilityProvider;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.network.NetworkHandler;
import com.mz.mobaspects.network.message.MobAspectMessage;
import com.mz.mobaspects.util.EntityAttributeUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ParasiteEntity extends MonsterEntity {

    private static final UUID KNOCKBACK_MODIFIER_IDENTIFIER = UUID.fromString("9b5b0d49-3666-4f3e-b836-ac62ea229a8e");

    private LivingEntity mobAttaching;
    private int currentTicks = 0;
    private int ticksToAttach = 60;
    private Set<AspectEnum> carryingAspects = new HashSet<>();

    public ParasiteEntity(EntityType<? extends ParasiteEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public ParasiteEntity(World worldIn , Set<AspectEnum> carryingAspects , int ticksToAttach) {
        super(CustomEntityRegister.ASPECT_PARASITE.get(), worldIn);
        this.carryingAspects = carryingAspects;
        this.ticksToAttach = ticksToAttach;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, true));
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if(mobAttaching != null){
            return true;
        }
        if(entityIn instanceof ParasiteEntity){
            return true;
        }

        if(entityIn instanceof MonsterEntity) {
            mobAttaching = ((MonsterEntity)entityIn);
            ((MonsterEntity)entityIn).addPotionEffect(new EffectInstance(Effects.GLOWING, 120));
            this.addPotionEffect(new EffectInstance(Effects.GLOWING , 120));

            EntityAttributeUtils.ApplyAttributeModifier((MonsterEntity)entityIn, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_MODIFIER_IDENTIFIER, 1.0f,
                    AttributeModifier.Operation.ADDITION, "Parasite knockback immune");

            EntityAttributeUtils.ApplyAttributeModifier(this, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_MODIFIER_IDENTIFIER, 1.0f,
                    AttributeModifier.Operation.ADDITION, "Parasite knockback immune");
        }
        return true;
    }

    private void attachToMob(){
        mobAttaching.getCapability(AspectCapabilityProvider.ASPECT_CAPABILITY).ifPresent(aspectMob -> {
            Set<AspectEnum> aspectsToAdd = new HashSet<>(carryingAspects);
            aspectsToAdd.removeAll(aspectMob.getAspectCodes());

            aspectsToAdd.forEach(code ->
                    AspectManager.INSTANCE.getAspect(code).getHandler().handleOnSpawn(mobAttaching));

            aspectMob.getAspectCodes().addAll(aspectsToAdd);
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.TRACKING_ENTITY.with(() -> this),
                    new MobAspectMessage(mobAttaching.getEntityId() , aspectMob.getAspectCodes())
            );
        });

        EntityAttributeUtils.removeAttributeModifier(mobAttaching , Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_MODIFIER_IDENTIFIER);

        this.remove();
    }

    public void tick() {
        this.renderYawOffset = this.rotationYaw;
        if(!this.world.isRemote){
            // mobAttaching died in the middle
            if(mobAttaching != null && !mobAttaching.isAlive()){
                mobAttaching = null;
                EntityAttributeUtils.removeAttributeModifier(this , Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_MODIFIER_IDENTIFIER);
                currentTicks = 0;
            } else if(this.isAlive() && mobAttaching != null && currentTicks < ticksToAttach){
                ++currentTicks;
                if(currentTicks >= ticksToAttach){
                    attachToMob();
                }
            }
        }
        super.tick();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.13F;
    }

    public static AttributeModifierMap.MutableAttribute getCustomAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 8.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMITE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_ENDERMITE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMITE_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.15F, 1.0F);
    }

    /**
     * Set the render yaw offset
     */
    public void setRenderYawOffset(float offset) {
        this.rotationYaw = offset;
        super.setRenderYawOffset(offset);
    }

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset() {
        return 0.1D;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void livingTick() {
        super.livingTick();
        if (this.world.isRemote) {
            for(int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.PORTAL, this.getPosXRandom(0.5D), this.getPosYRandom(), this.getPosZRandom(0.5D), (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
            }
        }
    }

    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }
}
