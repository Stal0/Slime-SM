package com.stalixo.epifania.util;

import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Mob;

import java.util.Random;

public class MobStarRating {

    private static final Random random = new Random();

    public static void applyStarRating(Mob mob, int baseLevel) {
        // Usa a Capability para recuperar ou definir os valores de starRating e mobLevel
        mob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(mobAttributes -> {

            int starRating;
            int boostedLevel;

            // Se o starRating não foi definido, gera novos valores
            if (mobAttributes.getRarity() == 0) {
                starRating = calculateRarity();
                boostedLevel = calculateBoostedLevel(baseLevel, starRating);

                // Armazena na Capability
                mobAttributes.setRarity(starRating);
                mobAttributes.setMobLevel(boostedLevel);
            } else {
                // Se já foi definido, recupera os valores existentes
                starRating = mobAttributes.getRarity();
                boostedLevel = mobAttributes.getMobLevel();
            }

            // Define o nome customizado com base no nível e raridade
            setCustomNameLevel(starRating, mob, boostedLevel);

        });
    }

    private static int calculateRarity() {
        // Defina as porcentagens de probabilidade para cada estrela
        int randomValue = random.nextInt(10000); // Gera um número aleatório de 0 a 9999

        if (randomValue < 6000) {
            return 1;
        } else if (randomValue < 8500) {
            return 2;
        } else if (randomValue < 9500) {
            return 3;
        } else if (randomValue < 9800) {
            return 4;
        } else if (randomValue < 9950) {
            return 5;
        } else {
            return 6;
        }
    }

    private static int calculateBoostedLevel(int baseLevel, int starRating) {
        // Multiplica o nível do mob de acordo com a quantidade de estrelas
        switch (starRating) {
            case 1:
                return baseLevel; // 100% do nível base
            case 2:
                return (int) (baseLevel * 1.2); // 120% do nível base
            case 3:
                return (int) (baseLevel * 1.4); // 140% do nível base
            case 4:
                return (int) (baseLevel * 1.6); // 160% do nível base
            case 5:
                return (int) (baseLevel * 1.8); // 180% do nível base
            case 6:
                return (int) (baseLevel * 2.5); // 250% do nível base
            default:
                return baseLevel;
        }
    }

    private static void setCustomNameLevel(int starRating, Mob mob, int level) {
        // Aplica o nome customizado ao mob com base na raridade e no nível
        switch (starRating) {
            case 1:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.BOLD));
                break;
            case 2:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.BOLD));
                break;
            case 3:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD));
                break;
            case 4:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.DARK_PURPLE).withStyle(ChatFormatting.BOLD));
                break;
            case 5:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
                break;
            case 6:
                mob.setCustomName(Component.literal("Level: " + level).withStyle(ChatFormatting.BLACK).withStyle(ChatFormatting.BOLD));
                break;
        }
    }
}
