package com.stalixo.epifania.util;

import com.stalixo.epifania.capability.PlayerAttributesProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;

import static com.stalixo.epifania.EpifaniaRPG.configManager;

public class MobCalculationXP {


    public static double calculateTotalXp(ServerPlayer player, Mob mob) {
        double baseXp = configManager.getConfig().getXpBase(mob);

        // Obter XP baseado no nível do mob
        double xpFromLevel = calculateXpBasedOnLevel(mob.getPersistentData().getInt("mobLevel"), baseXp);

        // Ajustar com base na diferença de nível entre o jogador e o mob
        double xpFromLevelDifference = calculateXpBasedOnLevelDifference(player, mob, xpFromLevel);

        // Ajustar com base na raridade do mob
        double xpFromRarity = calculateXpBasedOnRarity(mob, xpFromLevelDifference);

        return xpFromRarity;
    }

    private static double calculateXpBasedOnRarity(Mob mob, double xpFromLevelDifference) {

        int rarity = mob.getPersistentData().getInt("starRating");

        double rarityMultiplier;

        switch (rarity) {
            case 1: // Comum
                rarityMultiplier = 1.0; // Nenhum bônus
                break;
            case 2: // Incomum
                rarityMultiplier = 1.05; // 5% de bônus
                break;
            case 3: // Raro
                rarityMultiplier = 1.10; // 10% de bônus
                break;
            case 4: // Épico
                rarityMultiplier = 1.3; // 30% de bônus
                break;
            case 5: // Lendário
                rarityMultiplier = 1.5; // 50% de bônus
                break;
            case 6: // Mítico
                rarityMultiplier = 1.75; // 75% de bônus
                break;
            default:
                rarityMultiplier = 1.0; // Se não tiver raridade definida, usa o padrão (comum)
                break;
        }

        // Retornar o XP ajustado com base na raridade do mob
        return xpFromLevelDifference * rarityMultiplier;
    }

    private static double calculateXpBasedOnLevelDifference(ServerPlayer player, Mob mob, double xpFromLevel) {

        int playerLevel = player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES)
                .map(attributes -> attributes.getLevelPlayer())
                .orElse(0);

        int mobLevel = mob.getPersistentData().getInt("mobLevel");

        int levelDifference = mobLevel - playerLevel;

        double xpMultiplier;

        if (levelDifference > 5) {
            xpMultiplier = 1.3;  // Máximo de 130% XP para mobs muito mais fortes
        } else if (levelDifference < -5) {
            xpMultiplier = 0.5;  // Mínimo de 50% XP para mobs muito mais fracos
        } else {
            xpMultiplier = 1 + (levelDifference * 0.02); // Ajuste proporcional por diferença de nível (2% por nível)
        }

        // Retornar o XP ajustado com base na diferença de nível
        return xpFromLevel * xpMultiplier;
    }

    private static double calculateXpBasedOnLevel(int mobLevel, double baseXp) {
        return baseXp * (Math.log(mobLevel + 1) / Math.log(2));
    }

}
