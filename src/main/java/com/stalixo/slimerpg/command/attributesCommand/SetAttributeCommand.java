package com.stalixo.slimerpg.command.attributesCommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import com.stalixo.slimerpg.enums.Attributes;
import com.stalixo.slimerpg.event.customEvent.PlayerAttributeUpdateEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
public class SetAttributeCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("setattribute")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.argument("attribute", StringArgumentType.word())
                                .then(Commands.argument("value", IntegerArgumentType.integer(0, 10000))
                                        .executes(SetAttributeCommand::setAttribute)))));
    }

    private static int setAttribute(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = EntityArgument.getPlayer(context, "player");  // Obtém o jogador especificado
            String attributeName = StringArgumentType.getString(context, "attribute");  // Obtém o nome do atributo
            int value = IntegerArgumentType.getInteger(context, "value");  // Obtém o valor do atributo

            Optional<Attributes> attributeOpt = getAttributeByName(attributeName);

            if (attributeOpt.isPresent()) {
                Attributes attribute = attributeOpt.get();
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    attributes.setAttribute(attribute, value);
                    player.sendSystemMessage(Component.literal("Attribute: " + attributeName + " of Player: " + player.getName() + " has been set to " + value).withStyle(ChatFormatting.LIGHT_PURPLE));

                    MinecraftForge.EVENT_BUS.post(new PlayerAttributeUpdateEvent(player));
                });
            } else {
                context.getSource().sendFailure(Component.literal("Invalid attribute: " + attributeName));
            }
        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("Failed to retrieve player: " + e.getMessage()));
        }
        return 1;
    }


    protected static Optional<Attributes> getAttributeByName(String attributeName) {
        for (Attributes attributes : Attributes.values()) {
            if (attributes.name().equalsIgnoreCase(attributeName)) {
                return Optional.of(attributes);
            }
        }
        return Optional.empty();
    }

}
