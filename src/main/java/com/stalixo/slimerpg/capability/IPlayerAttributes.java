package com.stalixo.slimerpg.capability;

import com.stalixo.slimerpg.enums.Attributes;

public interface IPlayerAttributes {
    int getAttribute(Attributes attribute);
    void setAttribute(Attributes attribute, int value);

    int getAttributePoints();
    void addAttributePoints(int points);
    boolean spendAttributePoints(Attributes attribute, int points);
}
