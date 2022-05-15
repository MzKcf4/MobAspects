package com.mz.mobaspects.constants;

import java.util.HashMap;
import java.util.Map;

public enum AspectEnum {
    BERSERK(0, "berserk"),
    LEECH(1 , "leech"),
    CREEPER(2 , "creeper"),
    STICKY_BOMB(3 , "stickybomb"),
    ENDER_MAN(4 , "enderman"),
    VENGEANCE(5 , "vengeance"),
    VAMPIRE(6 , "vampire"),
    RIDER(7 , "rider"),
    EXTRA_LIFE(8 , "extralife"),
    DAMAGE_TRANSFER(9 , "damagetransfer"),
    GLASS_CANON(10 , "glasscanon"),
    SWAP(11 , "swap"),
    SWIFT(12, "swift"),
    DAMAGING_EFFECTS(13 , "damagingeffects"),
    DEBUFF(14 , "debuff"),
    HEAVY(15 , "heavy"),
    SIREN(16, "siren"),
    STOIC(17, "stoic"),
    GHAST_BUDDY(18, "ghastbuddy"),
    BERRY(19, "berry"),
    UNDYING_AURA(20, "undying_aura"),
    OVERLOAD(21,"overload");


    private static final Map<Integer , AspectEnum> ID_TO_ENUM_MAP = new HashMap<>();

    static {
        for(AspectEnum e : values()){
            ID_TO_ENUM_MAP.put(e.id , e);
        }
    }

    private final int id;
    private final String name;

    private AspectEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return name;
    }

    public static AspectEnum fromId(int id){
        return ID_TO_ENUM_MAP.get(id);
    }
}