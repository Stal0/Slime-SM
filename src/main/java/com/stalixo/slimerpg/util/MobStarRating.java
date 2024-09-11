package com.stalixo.slimerpg.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.monster.Monster;

import java.util.Random;

public class MobStarRating {

    private static final Random random = new Random();

    public static void applyStarRating(Monster mob, int baseLevel) {
        int starRating = calculateStarRating();
        int boostedLevel = calculateBoostedLevel(baseLevel, starRating);

        CompoundTag data = mob.getPersistentData();

        // Define o nome do mob com as estrelas e o novo nível
        String starString = getStarString(starRating);

        data.putInt("starRating", starRating);
        data.putInt("boostedLevel", boostedLevel);

        mob.setCustomName(Component.literal(starString + " " + mob.getName().getString() + " [Level: " + boostedLevel + "]"));
    }

    private static int calculateStarRating() {
        // Defina as porcentagens de probabilidade para cada estrela
        int randomValue = random.nextInt(10000); // Gera um número aleatório de 0 a 99

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

    private static String getStarString(int starRating) {
        String stars = "";
        switch (starRating) {
            case 1:
                stars = "§7★"; // Cinza
                break;
            case 2:
                stars = "§a★★"; // Verde
                break;
            case 3:
                stars = "§9★★★"; // Azul
                break;
            case 4:
                stars = "§5★★★★"; // Roxo
                break;
            case 5:
                stars = "§6★★★★★"; // Dourado
                break;
            case 6:
                stars = "§0★★★★★★"; // Preto
                break;
        }
        return stars;
    }
}
