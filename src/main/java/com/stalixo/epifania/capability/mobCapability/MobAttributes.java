package com.stalixo.epifania.capability.mobCapability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class MobAttributes implements IMobAttributes{

    private int mobLevel = 0;
    private int rarity = 0;
    private int particleCooldown = 0;

    @Override
    public int getMobLevel() {
        return this.mobLevel;
    }

    @Override
    public void setMobLevel(int level) {
        this.mobLevel = level;
    }

    @Override
    public int getRarity() {
        return this.rarity;
    }

    @Override
    public void setRarity(int rating) {
        this.rarity = rating;
    }

    public int getParticleCooldown() {
        return particleCooldown;
    }

    public void setParticleCooldown(int particleCooldown) {
        this.particleCooldown = particleCooldown;
    }

    public void copyFrom(MobAttributes source) {
        this.mobLevel = source.getMobLevel();
        this.rarity = source.getRarity();
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("mobLevel", mobLevel);
        nbt.putInt("rarity", rarity);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.mobLevel = nbt.getInt("mobLevel");
        this.rarity = nbt.getInt("rarity");
    }
}
