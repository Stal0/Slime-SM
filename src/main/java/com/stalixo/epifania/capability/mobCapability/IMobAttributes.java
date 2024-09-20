package com.stalixo.epifania.capability.mobCapability;

import com.stalixo.epifania.enums.Attributes;

public interface IMobAttributes {

    int getMobLevel();
    void setMobLevel(int value);

    int getRarity();
    void setRarity(int value);

    int getParticleCooldown();
    void setParticleCooldown(int value);
}
