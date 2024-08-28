package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.enums.MobExperience;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber
public class PlayerAttributeModifierHandler {

    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("1b94c1bd-c13c-4bf2-83a3-2c9e2c7f3d24");
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("7b44e0bd-d46c-41c6-9df0-59af4c5d4fdf");


    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyAttributeModifiers(player);
        }
    }

    public static void applyAttributeModifiers(Player player) {
        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {

            // Remova modificadores existentes antes de aplicar novos
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_UUID);
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);

            // Modificador de velocidade baseado no atributo Dexterity
            double speedModifierValue = attributes.getDexterity() * 0.01; // Exemplo: cada ponto em Dexterity aumenta 1% na velocidade
            AttributeModifier speedModifier = new AttributeModifier(SPEED_MODIFIER_UUID, "Dexterity Speed Boost", speedModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(speedModifier);

            // Modificador de ataque baseado no atributo Strength
            double attackModifierValue = attributes.getStrength() * 0.1; // Exemplo: cada ponto em Strength adiciona 0.5 de dano
            AttributeModifier attackModifier = new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Strength Attack Boost", attackModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(attackModifier);
        });
    }


    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        // Verifica se o evento envolve um monstro e um jogador
        System.out.println(event.getSource().getEntity());
        System.out.println(event.getEntity().getName());
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getEntity() instanceof Monster) {
            Monster mob = (Monster) event.getEntity();

            // Converte o nome do mob para string para procurar no enum
            String mobName = mob.getName().getString(); // Isso pega o nome do mob, como "zombie", "creeper", etc.
            System.out.println(mobName);
            // Obtém a experiência do enum
            int experiencePoints = MobExperience.getExperienceForMob(mobName);

            // Se o mob fornecer experiência, atualize a capability do jogador
            if (experiencePoints > 0) {
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    // Adiciona os pontos de experiência
                    attributes.addExperiencePoints(experiencePoints);

                    // Verifica se o jogador deve subir de nível
                    while (attributes.getExperiencePoints() >= attributes.getExperienceToNextLevel()) {
                        player.sendSystemMessage(Component.literal("Você subiu de nível!").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));
                        attributes.levelUp();
                    }
                });
            }
        }
    }
}
