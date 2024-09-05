package com.stalixo.slimerpg.config;

import java.util.Map;

public class Config {
    public int levelCap;
    public double maxPlayerSpeed;
    public Map<String, Double> mobsExperience;

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

    public Map<String, Double> getMobsExperience() {
        return mobsExperience;
    }

    public void setMobsExperience(Map<String, Double> mobsExperience) {
        this.mobsExperience = mobsExperience;
    }
}
