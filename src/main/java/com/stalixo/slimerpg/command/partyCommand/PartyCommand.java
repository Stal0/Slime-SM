package com.stalixo.slimerpg.command.partyCommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.stalixo.slimerpg.party.PartyManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PartyCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("party")
                .then(Commands.literal("create")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PartyManager.createParty(player);
                            return 1;
                        }))
                .then(Commands.literal("invite")
                        .then(Commands.argument("player", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerPlayer invitee = context.getSource().getServer().getPlayerList().getPlayerByName(StringArgumentType.getString(context, "player"));
                                    if (invitee != null) {
                                        PartyManager.invitePlayer(player, invitee);
                                    } else {
                                        player.sendSystemMessage(Component.literal("Player not found."));
                                    }
                                    return 1;
                                })))
                .then(Commands.literal("accept")
                        .then(Commands.argument("leader", StringArgumentType.string())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    ServerPlayer leader = context.getSource().getServer().getPlayerList().getPlayerByName(StringArgumentType.getString(context, "leader"));
                                    if (leader != null) {
                                        PartyManager.acceptInvitation(player, leader);
                                    } else {
                                        player.sendSystemMessage(Component.literal("Leader not found."));
                                    }
                                    return 1;
                                })))
                .then(Commands.literal("list")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            PartyManager.listParty(player);
                            return 1;
                        }))
        );
    }
}
