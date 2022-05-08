package com.mz.mobaspects.config;

import com.mz.mobaspects.aspect.aspects.*;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

// Initialization steps in AspectMob.java :
// 1. Core and aspect configs are loaded here   ( ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC , MOD_ID + ".server.toml") )
// 2. AspectManager then do initialization      ( AspectManager.INSTANCE.InitializeAspectList() )
public final class ServerConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    // Define the available values here :
    public static final ForgeConfigSpec.ConfigValue<Integer> MAX_ASPECT_COUNT;
    public static final ForgeConfigSpec.DoubleValue XP_MULTIPLIER_PER_ASPECT;
    public static final ForgeConfigSpec.DoubleValue BASE_ASPECT_CHANCE;
    public static final ForgeConfigSpec.DoubleValue NEXT_ASPECT_CHANCE;

    public static final List<AbstractAspect> allAspectList = new ArrayList<>();

    static {
       allAspectList.add(new BerryAspect());
       allAspectList.add(new BerserkAspect());
       allAspectList.add(new CreeperAspect());
       allAspectList.add(new DamageTransferAspect());
       allAspectList.add(new DamagingEffectAspect());
       allAspectList.add(new DebuffAspect());
       allAspectList.add(new EndermanAspect());
       allAspectList.add(new ExtraLifeAspect());
       allAspectList.add(new GhastBuddyAspect());
       allAspectList.add(new GlassCanonAspect());
       allAspectList.add(new HeavyAspect());
       allAspectList.add(new LeechAspect());
       allAspectList.add(new RiderAspect());
       allAspectList.add(new SirenAspect());
       allAspectList.add(new StickyBombAspect());
       allAspectList.add(new StoicAspect());
       allAspectList.add(new SwapAspect());
       allAspectList.add(new SwiftAspect());
       allAspectList.add(new VampireAspect());
       allAspectList.add(new VengenanceAspect());
       allAspectList.add(new UndyingAuraAspect());

        BUILDER.push("mobaspects");

        BUILDER.comment("Maximum aspects a mob can have (-1 = unlimited)");
        MAX_ASPECT_COUNT = BUILDER.define("maxAspects" , MobAspectConstants.CONFIG_MAX_ASPECT_NO_LIMIT);

        BUILDER.comment("XP multiplier per aspect count");
        XP_MULTIPLIER_PER_ASPECT = BUILDER.defineInRange("xpMultiplier" , 1.5D , 1.0D , Double.MAX_VALUE);

        BUILDER.comment("Base chance for a mob to have at least 1 aspect ( e.g 0.5 = 50% )");
        BASE_ASPECT_CHANCE = BUILDER.defineInRange("baseChance" , 0.5D , 0D , 1.0D);

        BUILDER.comment("The chance for a mob to have next aspect ( e.g chance of having 2 aspects = 0.5 base * 0.5 next  = 0.25 (25%)  )");
        NEXT_ASPECT_CHANCE = BUILDER.defineInRange("nextChance" , 0.5D , 0D , 1.0D);

        for(AbstractAspect aspect : allAspectList){
            aspect.buildConfig(BUILDER);
        }

        BUILDER.pop();
        SPEC = BUILDER.build();

    }

}
