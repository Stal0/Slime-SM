package com.stalixo.slimerpg.command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.menu.PlayerAttributeMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

@Mod.EventBusSubscriber
public class OpenAttributeMenuCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("openattributes")
                .requires(source -> source.hasPermission(2))
                .executes(OpenAttributeMenuCommand::openMenu));
    }

    public static int openMenu(CommandContext<CommandSourceStack> dispatcher) {
        try {
            ServerPlayer player = dispatcher.getSource().getPlayerOrException();

            NetworkHooks.openScreen(player, new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.literal("Attribute Menu");
                }

                @Override
                public PlayerAttributeMenu createMenu(int id, Inventory playerInventory, Player playerEntity) {
                    return new PlayerAttributeMenu(id, playerInventory, new FriendlyByteBuf(Unpooled.buffer()));
                }
            }, packetBuffer -> {
                // Aqui você pode enviar dados adicionais para o cliente, se necessário
                packetBuffer.writeInt(player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES)
                        .orElseThrow(() -> new IllegalStateException("Player attributes not found"))
                        .getStrength());

                packetBuffer.writeInt(player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES)
                        .orElseThrow(() -> new IllegalStateException("Player attributes not found"))
                        .getIntelligence());

                packetBuffer.writeInt(player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES)
                        .orElseThrow(() -> new IllegalStateException("Player attributes not found"))
                        .getAttributePoints());
            });
            return 1;
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
