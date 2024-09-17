package com.stalixo.epifania.config;

import com.stalixo.epifania.config.world.WorldLevelMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;

import java.util.Map;
import java.util.Set;

public class Config {
    private int levelCap;
    private double maxPlayerSpeed;
    private int factorXpBase;
    private Map<String, Double> mobsExperience;
    private Set<WorldLevelMob> worldLevels;


    public int getLevelCap() {
        return this.levelCap;
    }

    public void setLevelCap(int levelCap) {
        this.levelCap = levelCap;
    }

    public double getMaxPlayerSpeed() {
        return maxPlayerSpeed;
    }

    public void setMaxPlayerSpeed(double maxPlayerSpeed) {
        this.maxPlayerSpeed = maxPlayerSpeed;
    }

    public Map<String, Double> getMobsExperience() {
        return mobsExperience;
    }

    public void setMobsExperience(Map<String, Double> mobsExperience) {
        this.mobsExperience = mobsExperience;
    }

    public int getFactorXpBase() {
        return factorXpBase;
    }

    public void setFactorXpBase(int factorXpBase) {
        this.factorXpBase = factorXpBase;
    }

    public Set<WorldLevelMob> getWorlds() {
        return worldLevels;
    }

    public void setWorlds(Set<WorldLevelMob> worlds) {
        this.worldLevels = worlds;
    }

    public double getXpBase(Mob mob) {

        EntityType<?> mobType = mob.getType();

        for (Map.Entry<String, Double> mobInMap : getMobsExperience().entrySet()) {
            if (mobType.toString().toLowerCase().contains(mobInMap.getKey())) {
                return mobInMap.getValue();
            }
        }
        return 0;
    }
}
