package com.stalixo.slimerpg.event;

import com.google.common.collect.ImmutableMultimap;
import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.config.ConfigManager;
import com.stalixo.slimerpg.enums.MobExperience;
import com.stalixo.slimerpg.event.customEvent.PlayerAttributeUpdateEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Map;
import java.util.UUID;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;

@Mod.EventBusSubscriber
public class PlayerAttributeModifierHandler {

    static ConfigManager configManager = Slimerpg.configManager;

    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("1b94c1bd-c13c-4bf2-83a3-2c9e2c7f3d24");
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("7b44e0bd-d46c-41c6-9df0-59af4c5d4fdf");
    private static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("4f37a1b3-4c61-4edb-b50e-6c5f4f5e4e6e");
    private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("3c8d45b4-bc4e-4b3e-a5e9-78d5f2e9a9c9");
    private static final UUID MAX_MANA_MODIFIER_UUID = UUID.fromString("e645641c-a89a-4591-887b-f119a7a1305f");

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyAttributeModifiers(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerModifyAttributes(PlayerAttributeUpdateEvent event) {
        applyAttributeModifiers(event.getPlayer());
    }

    public static void applyAttributeModifiers(ServerPlayer player) {

        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {

            // Remova modificadores existentes antes de aplicar novos
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_UUID);
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
            player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_MODIFIER_UUID);
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(MAX_HEALTH_MODIFIER_UUID);
            player.getAttribute(MAX_MANA.get()).removeModifier(MAX_MANA_MODIFIER_UUID);


            // Modificador de velocidade baseado no atributo Dexterity
            double speedModifierValue = attributes.getDexterity() * 0.005;

            speedModifierValue = Math.min(speedModifierValue, configManager.getConfig().maxPlayerSpeed);

            AttributeModifier speedModifier = new AttributeModifier(SPEED_MODIFIER_UUID, "Dexterity Speed Boost", speedModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(speedModifier);

            // Modificador de ataque baseado no atributo Strength
            double attackModifierValue = attributes.getStrength() * 0.1; // Exemplo: cada ponto em Strength adiciona 0.5 de dano
            AttributeModifier attackModifier = new AttributeModifier(ATTACK_DAMAGE_MODIFIER_UUID, "Strength Attack Boost", attackModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(attackModifier);

            double maxHealthModifierValue = attributes.getConstitution() * 0.2;
            AttributeModifier maxHealthModifier = new AttributeModifier(MAX_HEALTH_MODIFIER_UUID, "Max Health Boost", maxHealthModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.MAX_HEALTH).addTransientModifier(maxHealthModifier);

            double attackSpeedModifierValue = attributes.getDexterity();
            AttributeModifier attackSpeedModifier = new AttributeModifier(ATTACK_SPEED_MODIFIER_UUID, "Attack Speed Boost", attackSpeedModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(attackSpeedModifier);

            float manaModifier = attributes.getIntelligence() * 2f;
            AttributeModifier maxManaModifier = new AttributeModifier(MAX_MANA_MODIFIER_UUID, "Mana Modifier", manaModifier, AttributeModifier.Operation.ADDITION);
            player.getAttribute(MAX_MANA.get()).addTransientModifier(maxManaModifier);
        });
    }


    @SubscribeEvent
    public static void onMobDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player && event.getEntity() instanceof Monster) {
            Monster mob = (Monster) event.getEntity();

            // Converte o nome do mob para string para procurar no enum
            String mobName = mob.getName().getString(); // Isso pega o nome do mob, como "zombie", "creeper", etc.
            // Obtém a experiência do enum
            int experiencePoints = MobExperience.getExperienceForMob(mobName);

            if (experiencePoints > 0) {
                double tempExperiencePoints = experiencePoints;

                for (Map.Entry<String, Double> entry : configManager.getConfig().getMobExperienceMultipliers().entrySet()) {
                    if (entry.getKey().equalsIgnoreCase(mobName)) {
                        tempExperiencePoints = tempExperiencePoints * entry.getValue();
                    }
                }

                double finalExperiencePoints = tempExperiencePoints;
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    // Adiciona os pontos de experiência
                    attributes.addExperiencePoints((int) finalExperiencePoints);

                    // Verifica se o jogador deve subir de nível
                    while (attributes.getExperiencePoints() >= attributes.getExperienceToNextLevel()) {
                        //Verifica se o jogador já chegou no nível máximo
                        if (attributes.getLevelPlayer() <= configManager.getConfig().levelCap) {
                            attributes.levelUp();
                            player.sendSystemMessage(Component.literal("Você subiu de nível!").withStyle(ChatFormatting.LIGHT_PURPLE, ChatFormatting.BOLD));
                        } else {
                            attributes.setExperiencePoints(attributes.getExperienceToNextLevel() - 1);
                        }
                    }
                });
            }
        }
    }
}
