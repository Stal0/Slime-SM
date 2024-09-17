package com.stalixo.epifania.enums;

public enum Attributes {
    STRENGTH("strength"),
    DEXTERITY("dexterity"),
    CONSTITUTION("constitution"),
    WISDOM("wisdom"),
    INTELLIGENCE("intelligence");

    private final String nbtKey;

    Attributes(String nbtKey) {
        this.nbtKey = nbtKey;
    }

    public String getNbtKey() {
        return nbtKey;
    }
}
