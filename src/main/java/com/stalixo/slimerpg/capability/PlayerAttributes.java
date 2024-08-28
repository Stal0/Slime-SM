package com.stalixo.slimerpg.capability;

import com.stalixo.slimerpg.enums.Attributes;
import net.minecraft.nbt.CompoundTag;

import java.util.EnumMap;

public class PlayerAttributes implements IPlayerAttributes {

    private final EnumMap<Attributes, Integer> attributes = new EnumMap<>(Attributes.class);
    private int attributePoints;
    private double experiencePoints;
    private int levelPlayer;

    public PlayerAttributes() {
        // Inicializar atributos com valor padrão (por exemplo, 0)
        for (Attributes attribute : Attributes.values()) {
            attributes.put(attribute, 0);
        }
        this.attributePoints = 0;
        this.experiencePoints = 0;
        this.levelPlayer = 1;
    }

    // Métodos específicos para obter e definir cada atributo
    public int getStrength() {
        return attributes.get(Attributes.STRENGTH);
    }

    public void setStrength(int value) {
        attributes.put(Attributes.STRENGTH, value);
    }

    public int getIntelligence() {
        return attributes.get(Attributes.INTELLIGENCE);
    }

    public void setIntelligence(int value) {
        attributes.put(Attributes.INTELLIGENCE, value);
    }

    public int getDexterity() {
        return attributes.get(Attributes.DEXTERITY);
    }

    public void setDexterity(int value) {
        attributes.put(Attributes.DEXTERITY, value);
    }

    public int getConstitution() {
        return attributes.get(Attributes.CONSTITUTION);
    }

    public void setConstitution(int value) {
        attributes.put(Attributes.CONSTITUTION, value);
    }

    public int getWisdom() {
        return attributes.get(Attributes.WISDOM);
    }

    public void setWisdom(int value) {
        attributes.put(Attributes.WISDOM, value);
    }

    // Métodos para gerenciamento de pontos de atributo
    public int getAttributePoints() {
        return attributePoints;
    }

    public void addAttributePoints(int points) {
        this.attributePoints += points;
    }

    public double getExperiencePoints() {
        return experiencePoints;
    }

    public void addExperiencePoints(int points) {
        this.experiencePoints += points;
    }

    public int getLevelPlayer() {
        return levelPlayer;
    }

    public void setLevelPlayer(int levelPlayer) {
        this.levelPlayer = levelPlayer;
    }

    @Override
    public int getAttribute(Attributes attribute) {
        return attributes.get(attribute);
    }

    @Override
    public void setAttribute(Attributes attribute, int value) {
        attributes.put(attribute, value);
    }

    public int getExperienceToNextLevel() {
        // Exemplo simples: 100 pontos de experiência para subir de nível
        return levelPlayer * 100;
    }

    public boolean spendAttributePoints(Attributes attribute, int points) {
        if (this.attributePoints >= points) {
            this.attributePoints -= points;
            setAttribute(attribute, getAttribute(attribute) + points);
            return true;
        }
        return false;
    }

    public void levelUp() {

        this.levelPlayer++;
        this.experiencePoints -= getExperienceToNextLevel();
        this.attributePoints += 5;
    }

    // Copiar dados de outra instância
    public void copyFrom(PlayerAttributes source) {
        for (Attributes attribute : Attributes.values()) {
            this.attributes.put(attribute, source.getAttribute(attribute));
        }
        this.attributePoints = source.getAttributePoints();
    }

    public void saveNBTData(CompoundTag nbt) {
        for (Attributes attribute : Attributes.values()) {
            nbt.putInt(attribute.getNbtKey(), attributes.get(attribute));
            System.out.println("Salvando " + attribute.getNbtKey() + ": " + attributes.get(attribute));
        }
        nbt.putInt("attributePoints", attributePoints);
        nbt.putDouble("experiencePoints", experiencePoints);
        nbt.putInt("levelPlayer", levelPlayer);
    }

    public void loadNBTData(CompoundTag nbt) {
        for (Attributes attribute : Attributes.values()) {
            int value = nbt.getInt(attribute.getNbtKey());
            this.attributes.put(attribute, value);
        }
        this.attributePoints = nbt.getInt("attributePoints");
        this.experiencePoints = nbt.getDouble("experiencePoints");
        this.levelPlayer = nbt.getInt("levelPlayer");

    }

}
