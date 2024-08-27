package com.stalixo.slimerpg.enums;

public enum MobExperience {
    ZOMBIE(10),
    SKELETON(15),
    CREEPER(20),
    ENDERMAN(50),
    WARDEN(500),
    PIGLIN(20);

    private final int experiencePoints;

    MobExperience(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public static int getExperienceForMob(String mobName) {
        try {
            return MobExperience.valueOf(mobName.toUpperCase()).getExperiencePoints();
        } catch (IllegalArgumentException e) {
            return 0; // Retorna 0 se o mob não estiver no enum
        }
    }
}
