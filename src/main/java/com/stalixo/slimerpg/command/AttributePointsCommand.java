package com.stalixo.slimerpg.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.enums.Attributes;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
public class AttributePointsCommand {
/*
    @SubscribeEvent
    public static void onCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("strength")
                .then(Commands.argument("points", IntegerArgumentType.integer()))
                .executes(AttributePointsCommand::appendPoints));

    }

    private static int appendPoints(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            int value = IntegerArgumentType.getInteger(context, "points");

            Optional<Attributes> attributeOpt = getAttributeByName("strength");

            if (attributeOpt.isPresent()) {
                Attributes attribute = attributeOpt.get();
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    attributes.setAttribute(attribute, value);
                });
            } else {
                //  context.getSource().sendFailure(Component.literal("Invalid attribute: " + attributeName));
            }

        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("Failed to retrieve player: " + e.getMessage()));
        }
        return 1;
    }

    private static Optional<Attributes> getAttributeByName(String attributeName) {
        for (Attributes attributes : Attributes.values()) {
            if (attributes.name().equalsIgnoreCase(attributeName)) {
                return Optional.of(attributes);
            }
        }
        return Optional.empty();
    } */
}
