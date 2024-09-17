package com.stalixo.epifania.zones;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.entity.monster.Monster;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZoneManager {
    private static ZoneManager instance;
    private List<Zone> zones;
    private static final File ZONE_FILE = new File("config/slimerpg/zones.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ZoneManager() {
        this.zones = new ArrayList<>();
        loadZones();
    }

    public static ZoneManager getInstance() {
        if (instance == null) {
            instance = new ZoneManager();
        }
        return instance;
    }

    public void addZone(Zone zone) {
        zones.add(zone);
        saveZones();  // Salva as zonas após adicionar uma nova
    }

    public void removeZone(String name) {
        zones.removeIf(zone -> zone.getName().equals(name));
        saveZones();  // Salva as zonas após remover
    }

    public List<Zone> getZones() {
        return zones;
    }

    private void loadZones() {
        if (ZONE_FILE.exists()) {
            try (FileReader reader = new FileReader(ZONE_FILE)) {
                Zone[] loadedZones = GSON.fromJson(reader, Zone[].class);
                zones = new ArrayList<>(List.of(loadedZones));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ZONE_FILE.getParentFile().mkdirs();
        }
    }

    private void saveZones() {
        try (FileWriter writer = new FileWriter(ZONE_FILE)) {
            GSON.toJson(zones, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Zone getZoneByName(String name) {
        return zones.stream().filter(zone -> zone.getName().equals(name)).findFirst().orElse(null);
    }

    public int getMobLevel(Monster mob) {
        int x = (int) mob.getX();
        int y = (int) mob.getY();
        int z = (int) mob.getZ();
        for (Zone zone : zones) {
            if (zone.isInside(x, y, z)) {
                return zone.getMobLevel();
            }
        }
        // Se não estiver em nenhuma área predefinida, retorna um nível padrão
        return 1;
    }
}

