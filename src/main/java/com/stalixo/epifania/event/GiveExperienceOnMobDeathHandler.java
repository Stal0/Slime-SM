package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.capability.PlayerAttributesProvider;
import com.stalixo.epifania.config.ConfigManager;
import com.stalixo.epifania.util.MobCalculationXP;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GiveExperienceOnMobDeathHandler {

    static ConfigManager configManager = EpifaniaRPG.configManager;

    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getEntity() instanceof Mob mob) {

            double experiencePoints = MobCalculationXP.calculateTotalXp(player, mob);
            System.out.println(experiencePoints);
            System.out.println(mob.getPersistentData().getInt("mobLevel"));
            System.out.println(mob.getPersistentData().getInt("starRating"));


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
