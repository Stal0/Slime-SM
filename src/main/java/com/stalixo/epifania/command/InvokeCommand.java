package com.stalixo.epifania.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import com.stalixo.epifania.gui.AttributesPlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class InvokeCommand {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // Registra o comando com os parâmetros: nome do mob, raridade e nível
        dispatcher.register(Commands.literal("profile")
                .executes(InvokeCommand::openScreenPlayer));
    }

    private static int openScreenPlayer(CommandContext<CommandSourceStack> context) {
        if (context.getSource().getEntity() instanceof Player player) {
            Minecraft.getInstance().setScreen(new AttributesPlayerScreen(Component.literal("Perfil"), player));
            return 1;
        }
        return 0;
    }

}
