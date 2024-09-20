package com.stalixo.epifania.capability.playerCapability;

import com.stalixo.epifania.enums.Attributes;

public interface IPlayerAttributes {
    int getAttribute(Attributes attribute);
    void setAttribute(Attributes attribute, int value);

    int getAttributePoints();
    void addAttributePoints(int points);
    boolean spendAttributePoints(Attributes attribute, int points);
}
