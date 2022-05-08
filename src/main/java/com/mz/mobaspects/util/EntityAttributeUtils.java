package com.mz.mobaspects.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;

import java.util.UUID;

public class EntityAttributeUtils {

    private EntityAttributeUtils(){};

    public static void ApplyAttributeModifier(LivingEntity entity, Attribute attribute , UUID modifierUUID ,
                                              float amount, AttributeModifier.Operation operation , String modifierDescription){
        ModifiableAttributeInstance attr = entity.getAttribute(attribute);
        if(attr != null){
            if(attr.getModifier(modifierUUID) != null){
                attr.removeModifier(modifierUUID);
            }
            AttributeModifier speedModifier = createAttributeModifier(modifierUUID , amount , operation , modifierDescription);
            attr.applyNonPersistentModifier(speedModifier);
        }
    }

    private static AttributeModifier createAttributeModifier(UUID modifierUUID , float amount, AttributeModifier.Operation operation , String modifierDescription){
        return new AttributeModifier(modifierUUID, modifierDescription, amount, operation);
    }
}
