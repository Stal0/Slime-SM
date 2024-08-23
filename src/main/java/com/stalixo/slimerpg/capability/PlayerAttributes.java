package com.stalixo.slimerpg.capability;

import com.stalixo.slimerpg.enums.Attributes;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;

public class PlayerAttributes implements IPlayerAttributes {

    private final EnumMap<Attributes, Integer> attributes = new EnumMap<>(Attributes.class);

    public PlayerAttributes() {
        // Inicializar atributos com valor padrão (por exemplo, 0)
        for (Attributes attribute : Attributes.values()) {
            attributes.put(attribute, 0);
        }
    }

    public int getStrength() {
        return attributes.get(Attributes.STRENGTH);
    }

    public void setStrength(int strength) {
        attributes.put(Attributes.STRENGTH, strength);
    }

    public int getIntelligence() {
        return attributes.get(Attributes.INTELLIGENCE);
    }

    public void setIntelligence(int intelligence) {
        attributes.put(Attributes.INTELLIGENCE, intelligence);
    }

    public int getDexterity() {
        return attributes.get(Attributes.DEXTERITY);
    }

    public void setDexterity(int dexterity) {
        attributes.put(Attributes.DEXTERITY, dexterity);
    }

    public int getWisdom() {
        return attributes.get(Attributes.WISDOM);
    }

    public void setWisdom(int wisdom) {
        attributes.put(Attributes.WISDOM, wisdom);
    }

    public int getConstitution() {
        return attributes.get(Attributes.CONSTITUTION);
    }

    public void setConstitution(int constitution) {
        attributes.put(Attributes.CONSTITUTION, constitution);
    }

    // Métodos genéricos para acessar e modificar qualquer atributo
    public int getAttribute(Attributes attribute) {
        return attributes.get(attribute);
    }

    public void setAttribute(Attributes attribute, int value) {
        attributes.put(attribute, value);
    }

    public void copyFrom(PlayerAttributes source) {
        for (Attributes attribute : Attributes.values()) {
            this.attributes.put(attribute, source.getAttribute(attribute));
        }
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Attributes attribute : Attributes.values()) {
            nbt.putInt(attribute.getNbtKey(), attributes.get(attribute));
        }
    }

    public void loadNBTData(CompoundTag nbt) {
        for (Attributes attribute : Attributes.values()) {
            this.attributes.put(attribute, nbt.getInt(attribute.getNbtKey()));
        }
    }
}
