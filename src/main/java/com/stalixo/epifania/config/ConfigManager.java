package com.stalixo.epifania.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stalixo.epifania.config.world.WorldLevelMob;
import com.stalixo.epifania.enums.MobExperience;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/slimerpg/config.json");

    private Config config;

    private Set<WorldLevelMob> worldLevels;

    public ConfigManager() {
        loadConfig();
    }

    public Config getConfig() {
        return config;
    }

    private void loadConfig() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                config = GSON.fromJson(reader, Config.class);
                worldLevels = config.getWorlds();

                if (config == null) {
                    throw new NullPointerException("Config data is null!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.err.println("Failed to load config data: " + e.getMessage());
                // Você pode optar por regenerar o arquivo de configuração aqui, se necessário
            }
        } else {
            // Gerar novo arquivo de configuração
            config = new Config();
            config.setLevelCap(50);
            config.setMaxPlayerSpeed(0.02);
            config.setFactorXpBase(200);

            Map<String, Double> mobExperienceMultipliers = new HashMap<>();
            for (MobExperience mob : MobExperience.values()) {
                mobExperienceMultipliers.put(mob.getMobName(), 1.0);
            }
            config.setMobsExperience(mobExperienceMultipliers);

            worldLevels = new HashSet<>();
            addDefaultWorldLevels();
            config.setWorlds(worldLevels);

            saveConfig();
        }
    }


    public void saveConfig() {
        try {
            if (!CONFIG_FILE.getParentFile().exists()) {
                CONFIG_FILE.getParentFile().mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDefaultWorldLevels() {
        WorldLevelMob overworld = new WorldLevelMob();
        overworld.setDimensionName("minecraft:overworld");
        overworld.setMinLevel(10);
        overworld.setMaxLevel(50);
        worldLevels.add(overworld);

        WorldLevelMob nether = new WorldLevelMob();
        nether.setDimensionName("minecraft:the_nether");
        nether.setMinLevel(20);
        nether.setMaxLevel(60);
        worldLevels.add(nether);

        WorldLevelMob end = new WorldLevelMob();
        end.setDimensionName("minecraft:the_end");
        end.setMinLevel(30);
        end.setMaxLevel(70);
        worldLevels.add(end);
    }


    public WorldLevelMob getWorldLevelByDimension(String dimensionName) {
        return worldLevels.stream()
                .filter(world -> world.getDimensionName().equals(dimensionName))
                .findFirst()
                .orElse(null);
    }


}
