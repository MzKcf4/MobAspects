package com.mz.mobaspects.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

// Wrapper class for 'protected' Effect class
public class CustomEffect extends Effect {

    public static Effect UNDYING;

    protected CustomEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }
}
