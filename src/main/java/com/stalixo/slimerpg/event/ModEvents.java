package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.capability.PlayerAttributes;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Slimerpg.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).isPresent()) {
                event.addCapability(new ResourceLocation("slimerpg", "player_attributes"), new PlayerAttributesProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerAttributes.class);
    }

    @SubscribeEvent
    public static void onBreakBlock(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
            // Exibindo os atributos no chat
            String message = "Strength: " + attributes.getStrength();
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.AQUA));

            message = "Intelligence: " + attributes.getIntelligence();
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.AQUA));

            message = "Dexterity: " + attributes.getDexterity();
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.AQUA));

            message = "Constitution: " + attributes.getConstitution();
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.AQUA));

            message = "Wisdom: " + attributes.getWisdom();
            player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.AQUA));

        });
    }

}
