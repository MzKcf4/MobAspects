package com.mz.mobaspects.capability.aspect;

import com.mz.mobaspects.aspect.core.AspectMob;
import com.mz.mobaspects.aspect.core.IAspectMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

// Implement ICapabilitySerializable<INBT> , because require store data persistently
public class AspectCapabilityProvider implements ICapabilitySerializable<INBT> {

    // The unique instance of Aspect capability
    @CapabilityInject(IAspectMob.class)
    public static Capability<IAspectMob> ASPECT_CAPABILITY = null;
    // This will create one instance of the default instance when it is first needed. i.e. the first time you try to access it.
    // private final LazyOptional<IAspectMob> INSTANCE = LazyOptional.of(ASPECT_CAPABILITY::getDefaultInstance);
    final LazyOptional<IAspectMob> optional;

    // More on LazyOptional :
    // It is a wrapper around your capability instance (which may or may not necessarily exist yet)
    // and when your cap does exist, the LazyOptional will automatically get updated from holding null to holding your cap instance
    // (because its actually providing a pointer to your cap field, rather than pointing at what your cap field contained when the LazyOptional was created).

    final IAspectMob dto;

    public AspectCapabilityProvider(final LivingEntity livingEntity) {
        this.dto = new AspectMob(livingEntity);
        this.optional = LazyOptional.of(() -> dto);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nullable Capability<T> capability, Direction side) {
        return ASPECT_CAPABILITY.orEmpty(capability , optional);
    }

    @Override
    public INBT serializeNBT() {
        return ASPECT_CAPABILITY.writeNBT(dto, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        ASPECT_CAPABILITY.readNBT(dto, null, nbt);
    }

    public void invalidate() {
        this.optional.invalidate();
    }
}

