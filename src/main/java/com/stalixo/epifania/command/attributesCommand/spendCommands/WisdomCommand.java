package com.stalixo.epifania.command.attributesCommand.spendCommands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.epifania.capability.PlayerAttributesProvider;
import com.stalixo.epifania.enums.Attributes;
import com.stalixo.epifania.event.customEvent.PlayerAttributeUpdateEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WisdomCommand {

    @SubscribeEvent
    public static void onCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("wisdom")
                .then(Commands.literal("spend")
                        .then(Commands.argument("value", IntegerArgumentType.integer(1))
                        .executes(WisdomCommand::spendPoints))));

    }

    private static int spendPoints(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            int value = IntegerArgumentType.getInteger(context, "value");

            player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                if (attributes.getAttributePoints() >= value) {
                    attributes.spendAttributePoints(Attributes.WISDOM, value);
                    player.sendSystemMessage(Component.literal("You spent " + value + " points on Wisdom!"));

                    MinecraftForge.EVENT_BUS.post(new PlayerAttributeUpdateEvent(player));
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
