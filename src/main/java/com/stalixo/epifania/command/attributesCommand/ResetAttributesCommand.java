package com.stalixo.epifania.command.attributesCommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.stalixo.epifania.capability.playerCapability.PlayerAttributesProvider;
import com.stalixo.epifania.enums.Attributes;
import com.stalixo.epifania.event.customEvent.PlayerAttributeUpdateEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

import static com.stalixo.epifania.command.attributesCommand.SetAttributeCommand.getAttributeByName;

@Mod.EventBusSubscriber
public class ResetAttributesCommand {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("attributes")
                .then(Commands.literal("reset")
                .then(Commands.argument("attribute", StringArgumentType.word())
                .executes(ResetAttributesCommand::resetAttributes))));
    }

    private static int resetAttributes(CommandContext<CommandSourceStack> context) {
        try {
            ServerPlayer player = context.getSource().getPlayerOrException();
            String attributeName = StringArgumentType.getString(context, "attribute");

            Optional<Attributes> attributeOpt = getAttributeByName(attributeName);

            if (attributeOpt.isPresent()) {
                Attributes attribute = attributeOpt.get();
                player.getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).ifPresent(attributes -> {
                    for (Attributes attributes1 : Attributes.values()) {
                        if (attributes1.name().equalsIgnoreCase(attributeName)) {
                            attributes.addAttributePoints(attributes.getAttribute(attributes1));
                            attributes.setAttribute(attribute, 0);
                            player.sendSystemMessage(Component.literal("Attribute: " + attributeName + "has been reset!").withStyle(ChatFormatting.LIGHT_PURPLE));

                            MinecraftForge.EVENT_BUS.post(new PlayerAttributeUpdateEvent(player));
                        }
                    }
                });
            }

        } catch (CommandSyntaxException e) {
            context.getSource().sendFailure(Component.literal("Failed to retrieve player: " + e.getMessage()));
        }
        return 1;
    }

}
