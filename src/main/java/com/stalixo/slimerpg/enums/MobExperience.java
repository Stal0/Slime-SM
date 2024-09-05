package com.stalixo.slimerpg.enums;

public enum MobExperience {
    ZOMBIE("zombie"),
    SKELETON("skeleton"),
    CREEPER("creeper"),
    ENDERMAN("enderman"),
    WARDEN("warden"),
    PIGLIN("piglin"),
    PILLAGER("pillager");


    private final String mobName;

    MobExperience(String mobName) {
        this.mobName = mobName;
    }

    public String getMobName() {
        return mobName;
    }
}
