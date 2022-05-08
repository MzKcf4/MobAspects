package com.mz.mobaspects.entity;

import com.mz.mobaspects.MobAspects;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
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

    // UndyingTotemAspectEntity
}
