package com.mz.mobaspects.capability.aspect;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class AspectStorage implements Capability.IStorage<IAspectMob> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IAspectMob> capability, IAspectMob instance, Direction side) {
        CompoundNBT compoundNBT = new CompoundNBT();

        Set<AspectEnum> aspectCodeList = instance.getAspectCodes();
        compoundNBT.putIntArray(MobAspectConstants.ATTRIBUTE_KEY , aspectEnumToId(aspectCodeList));
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<IAspectMob> capability, IAspectMob instance, Direction side, INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        if(compoundNBT.contains(MobAspectConstants.ATTRIBUTE_KEY)){
            int[] codeArray = compoundNBT.getIntArray(MobAspectConstants.ATTRIBUTE_KEY);
            instance.setAspectCodes(aspectIdToEnum(codeArray));
        }
    }

    private static List<Integer> aspectEnumToId(Collection<AspectEnum> aspectCodeList){
        return aspectCodeList.stream().map(AspectEnum::getId).collect(Collectors.toList());
    }

    private static Set<AspectEnum> aspectIdToEnum(int[] aspectIdAry){
        return Arrays.stream(aspectIdAry)
                .mapToObj(AspectEnum::fromId)
                .collect(Collectors.toSet());
    }
}
