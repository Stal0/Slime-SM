package com.stalixo.slimerpg.config;

import java.util.Map;

public class Config {
    public int levelCap;
    public double maxPlayerSpeed;
    public Map<String, Double> mobExperienceMultipliers;

    public int getLevelCap() {
        return levelCap;
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

    public Map<String, Double> getMobExperienceMultipliers() {
        return mobExperienceMultipliers;
    }

    public void setMobExperienceMultipliers(Map<String, Double> mobExperienceMultipliers) {
        this.mobExperienceMultipliers = mobExperienceMultipliers;
    }
}
