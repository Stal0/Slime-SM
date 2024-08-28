package com.stalixo.slimerpg.command.attributesCommand.spendCommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.enums.Attributes;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DexterityCommand {

    @SubscribeEvent
    public static void onCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("dexterity")
                .then(Commands.literal("spend")
                        .then(Commands.argument("value", IntegerArgumentType.integer(1))
                        .executes(DexterityCommand::spendPoints))));

    }

    private static int spendPoints(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            int value = IntegerArgumentType.getInteger(context, "value");

            player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                if (attributes.getAttributePoints() >= value) {
                    attributes.spendAttributePoints(Attributes.DEXTERITY, value);
                    player.sendSystemMessage(Component.literal("You spent " + value + " points on Dexterity!"));
                } else {
                    context.getSource().sendFailure(Component.literal("Not enough attribute points!"));
                }
            });

        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("Failed to retrieve player: " + e.getMessage()));
        }
        return 1;
    }
}
