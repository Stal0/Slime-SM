package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.config.ConfigManager;
import com.stalixo.slimerpg.util.MobCalculationXP;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class GiveExperienceOnMobDeathHandler {

    static ConfigManager configManager = Slimerpg.configManager;

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getEntity() instanceof Mob mob) {

            double experiencePoints = MobCalculationXP.calculateTotalXp(player, mob);

            if (experiencePoints > 0) {
                System.out.println(experiencePoints);
                levelUpVerify(experiencePoints, player);
            }
        }
    }


    private static void levelUpVerify(double experiencePoints, ServerPlayer player) {
        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
            // Adiciona os pontos de experiência
            attributes.addExperiencePoints(experiencePoints);

            // Verifica se o jogador deve subir de nível
            while (attributes.getExperiencePoints() >= attributes.getExperienceToNextLevel()) {
                //Verifica se o jogador já chegou no nível máximo
                if (attributes.getLevelPlayer() <= configManager.getConfig().getLevelCap()) {
                    attributes.levelUp();
                    player.sendSystemMessage(Component.literal("Você subiu de nível!").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));
                } else {
                    attributes.setExperiencePoints(attributes.getExperienceToNextLevel() - 1);
                }
            }
        });
    }
}
