package com.mz.mobaspects.entity;

import com.mz.mobaspects.MobAspects;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermiteEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class  CustomEntityRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MobAspects.MOD_ID);

    public static final RegistryObject<EntityType<GhastBuddyEntity>> GHAST_BUDDY = ENTITY_TYPES.register("ghast_buddy", () ->
                    EntityType.Builder.<GhastBuddyEntity>create(GhastBuddyEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F)
                    .immuneToFire()
                    .build(new ResourceLocation(MobAspects.MOD_ID, "ghast_buddy").toString())
    );

    public static final RegistryObject<EntityType<UndyingTotemAspectEntity>> UNDYING_TOTEM = ENTITY_TYPES.register("undying_totem_aspect", () ->
            EntityType.Builder.<UndyingTotemAspectEntity>create(UndyingTotemAspectEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F)
                    .immuneToFire()
                    .build(new ResourceLocation(MobAspects.MOD_ID, "undying_totem_aspect").toString())
    );


    public static final RegistryObject<EntityType<OverloadCrystalEntity>> OVERLOAD_CRYSTAL = ENTITY_TYPES.register("overload_crystal", () ->
            EntityType.Builder.<OverloadCrystalEntity>create(OverloadCrystalEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F)
                    .immuneToFire()
                    .build(new ResourceLocation(MobAspects.MOD_ID, "overload_crystal").toString())
    );

    public static final RegistryObject<EntityType<ParasiteEntity>> ASPECT_PARASITE = ENTITY_TYPES.register("aspect_parasite", () ->
            EntityType.Builder.<ParasiteEntity>create(ParasiteEntity::new, EntityClassification.MONSTER)
                    .size(0.4F, 0.3F)
                    .trackingRange(8)
                    .build(new ResourceLocation(MobAspects.MOD_ID, "aspect_parasite").toString())
    );

    public static final RegistryObject<EntityType<AspectShieldEntity>> ASPECT_SHIELD = ENTITY_TYPES.register("aspect_shield", () ->
            EntityType.Builder.<AspectShieldEntity>create(AspectShieldEntity::new, EntityClassification.MISC)
                    .size(0.4F, 0.3F)
                    .trackingRange(8)
                    .build(new ResourceLocation(MobAspects.MOD_ID, "aspect_shield").toString())
    );
}
