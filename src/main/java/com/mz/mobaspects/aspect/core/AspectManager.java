package com.mz.mobaspects.aspect.core;

import com.mz.mobaspects.aspect.aspects.AbstractAspect;
import com.mz.mobaspects.config.ServerConfig;
import com.mz.mobaspects.constants.AspectEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.MonsterEntity;

import java.util.*;

public class AspectManager {

    public static final AspectManager INSTANCE = new AspectManager();

    private HashMap<AspectEnum , AbstractAspect> availableAspectMap;
    private List<AspectEnum> enabledAspectEnumList;

    private AspectManager(){}

    public void InitializeAspectList(){
        availableAspectMap = new HashMap<>();

        for(AbstractAspect aspect : ServerConfig.allAspectList){
            if(!aspect.isEnabled()){
                continue;
            }
            availableAspectMap.put(aspect.getCode() , aspect);
            aspect.applyConfigToHandler();
        }
        enabledAspectEnumList = new ArrayList<>(availableAspectMap.keySet());
    }

    public boolean canAttachAspect(Entity entity){
        return entity instanceof MonsterEntity;
    }

    public Set<AspectEnum> rollAspect(MobEntity entity){
        if(entity.getRNG().nextFloat() > ServerConfig.BASE_ASPECT_CHANCE.get().floatValue()){
            return new HashSet<>();
        }

        Set<AspectEnum> result = new HashSet<>();
        List<AspectEnum> clonedShuffleList = new ArrayList<>(enabledAspectEnumList);
        Collections.shuffle(clonedShuffleList);

        for (AspectEnum aspectEnum : clonedShuffleList) {
            result.add(aspectEnum);
            if (entity.getRNG().nextFloat() > ServerConfig.NEXT_ASPECT_CHANCE.get().floatValue()) {
                break;
            }
        }

        return result;
    }

    public AbstractAspect getAspect(AspectEnum code){
        return availableAspectMap.get(code);
    }
}
