package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.capability.PlayerAttributesProvider;
import com.stalixo.epifania.config.ConfigManager;
import com.stalixo.epifania.event.customEvent.PlayerAttributeUpdateEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.*;

@Mod.EventBusSubscriber
public class PlayerAttributeModifierHandler {

    static ConfigManager configManager = EpifaniaRPG.configManager;

    private static final UUID SPEED_MODIFIER_UUID = UUID.fromString("1b94c1bd-c13c-4bf2-83a3-2c9e2c7f3d24");
    private static final UUID ATTACK_DAMAGE_MODIFIER_UUID = UUID.fromString("7b44e0bd-d46c-41c6-9df0-59af4c5d4fdf");
    private static final UUID ATTACK_SPEED_MODIFIER_UUID = UUID.fromString("4f37a1b3-4c61-4edb-b50e-6c5f4f5e4e6e");
    private static final UUID MAX_HEALTH_MODIFIER_UUID = UUID.fromString("3c8d45b4-bc4e-4b3e-a5e9-78d5f2e9a9c9");
    private static final UUID MAX_MANA_MODIFIER_UUID = UUID.fromString("e645641c-a89a-4591-887b-f119a7a1305f");
    private static final UUID MANA_REGEN_MODIFIER_UUID = UUID.fromString("9582c7e1-c805-4bb9-985c-5837014b5a30");
    private static final UUID SPELL_POWER_MODIFIER_UUID = UUID.fromString("b3b75d3c-1dde-425a-a801-45732cb66a20");

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

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            applyAttributeModifiers(player);
        }
    }

    public static void applyAttributeModifiers(ServerPlayer player) {

        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {

            // Remova modificadores existentes antes de aplicar novos
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_MODIFIER_UUID);
            player.getAttribute(Attributes.ATTACK_DAMAGE).removeModifier(ATTACK_DAMAGE_MODIFIER_UUID);
            player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(ATTACK_SPEED_MODIFIER_UUID);
            player.getAttribute(Attributes.MAX_HEALTH).removeModifier(MAX_HEALTH_MODIFIER_UUID);
            player.getAttribute(MAX_MANA.get()).removeModifier(MAX_MANA_MODIFIER_UUID);
            player.getAttribute(AttributeRegistry.MANA_REGEN.get()).removeModifier(MANA_REGEN_MODIFIER_UUID);
            player.getAttribute(AttributeRegistry.SPELL_POWER.get()).removeModifier(SPELL_POWER_MODIFIER_UUID);


            // Modificador de velocidade baseado no atributo Dexterity
            double speedModifierValue = attributes.getDexterity() * 0.001;

            speedModifierValue = Math.min(speedModifierValue, configManager.getConfig().getMaxPlayerSpeed());

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

            double manaModifierValue = attributes.getIntelligence() * 0.5;
            AttributeModifier maxManaModifier = new AttributeModifier(MAX_MANA_MODIFIER_UUID, "Mana Modifier", manaModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(MAX_MANA.get()).addTransientModifier(maxManaModifier);

            double manaRegenModifierValue = attributes.getIntelligence() * 0.00001;
            AttributeModifier manaRegenModifier = new AttributeModifier(MANA_REGEN_MODIFIER_UUID, "Mana Regen Modifier", manaRegenModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(MANA_REGEN.get()).addTransientModifier(manaRegenModifier);

            double spellPowerModifierValue = attributes.getIntelligence() * 10;
            AttributeModifier spellPowerModifier = new AttributeModifier(SPELL_POWER_MODIFIER_UUID, "Spell Power Modifier", spellPowerModifierValue, AttributeModifier.Operation.ADDITION);
            player.getAttribute(SPELL_POWER.get()).addTransientModifier(spellPowerModifier);
        });
    }
}
