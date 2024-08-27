package com.stalixo.slimerpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ListAttributesCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("attributes")
                .requires(source -> source.hasPermission(1))
                .executes(ListAttributesCommand::listAttributes));
    }

    private static int listAttributes(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();

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

                message = "Attributes Points: " + attributes.getAttributePoints();
                player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.DARK_AQUA));

                message = "Level: " + attributes.getLevelPlayer();
                player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.DARK_AQUA));

                message = "Experience Points: " + attributes.getExperiencePoints();
                player.sendSystemMessage(Component.literal(message).withStyle(ChatFormatting.DARK_AQUA));


            });
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
