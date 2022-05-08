package com.mz.mobaspects.capability.aspect;

import com.mz.mobaspects.aspect.core.IAspectMob;
import com.mz.mobaspects.constants.AspectEnum;
import com.mz.mobaspects.constants.MobAspectConstants;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AspectStorage implements Capability.IStorage<IAspectMob> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IAspectMob> capability, IAspectMob instance, Direction side) {
        CompoundNBT compoundNBT = new CompoundNBT();

        List<AspectEnum> aspectCodeList = instance.getAspectCodeList();
        compoundNBT.putIntArray(MobAspectConstants.ATTRIBUTE_KEY , aspectEnumToId(aspectCodeList));
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<IAspectMob> capability, IAspectMob instance, Direction side, INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        if(compoundNBT.contains(MobAspectConstants.ATTRIBUTE_KEY)){
            int[] codeArray = compoundNBT.getIntArray(MobAspectConstants.ATTRIBUTE_KEY);
            instance.setAspectCodeList(aspectIdToEnum(codeArray));
        }
    }

    private static List<Integer> aspectEnumToId(List<AspectEnum> aspectCodeList){
        return aspectCodeList.stream().map(AspectEnum::getId).collect(Collectors.toList());
    }

    private static List<AspectEnum> aspectIdToEnum(int[] aspectIdAry){
        List<AspectEnum> result = new ArrayList<>();
        Arrays.stream(aspectIdAry).forEach(id -> result.add(AspectEnum.fromId(id)));
        return result;
    }
}
