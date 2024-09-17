package com.stalixo.epifania.config.world;

import net.minecraft.world.entity.monster.Monster;

public class WorldLevelMob {

    private String dimensionName;
    private int minLevel;
    private int maxLevel;

    public String getDimensionName() {
        return dimensionName;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getLevelByDistance(Monster mob) {
        double mobX = mob.getX();
        double mobZ = mob.getZ();

        int minLevel = this.getMinLevel();
        int maxLevel = this.getMaxLevel();

        double spawnX = 0.0;
        double spawnZ = 0.0;

        // Calcular a distância do mob até o ponto de origem (distância euclidiana 2D)
        double distanceFromSpawn = Math.sqrt(Math.pow(mobX - spawnX, 2) + Math.pow(mobZ - spawnZ, 2));

        double maxDistance = 10000; // Por exemplo, 10.000 blocos até o MaxLevel

        int calculatedLevel = minLevel;

        if (distanceFromSpawn >= maxDistance) {
            calculatedLevel = maxLevel; // Se ultrapassar a distância máxima, define o nível máximo
        } else {
            // Escalar o nível conforme a distância do spawn
            double levelRange = maxLevel - minLevel;
            calculatedLevel = (int) (minLevel + (distanceFromSpawn / maxDistance) * levelRange);
        }

        return calculatedLevel;
    }

}
