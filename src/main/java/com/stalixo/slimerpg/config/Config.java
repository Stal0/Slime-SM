package com.stalixo.slimerpg.config;

import com.stalixo.slimerpg.config.world.WorldLevelMob;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Config {
    private int levelCap;
    private double maxPlayerSpeed;
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

    public Set<WorldLevelMob> getWorlds() {
        return worldLevels;
    }

    public void setWorlds(Set<WorldLevelMob> worlds) {
        this.worldLevels = worlds;
    }
}
