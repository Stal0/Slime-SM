package com.stalixo.slimerpg.command.zoneCommand;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.stalixo.slimerpg.zones.Zone;
import com.stalixo.slimerpg.zones.ZoneManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber
public class CreateZoneCommand {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        dispatcher.register(Commands.literal("zone")
                .requires(source -> source.hasPermission(2))
                // Comando para criar uma nova zona
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .then(Commands.argument("valueX1", IntegerArgumentType.integer())
                                        .then(Commands.argument("valueY1", IntegerArgumentType.integer())
                                                .then(Commands.argument("valueZ1", IntegerArgumentType.integer())
                                                        .then(Commands.argument("valueX2", IntegerArgumentType.integer())
                                                                .then(Commands.argument("valueY2", IntegerArgumentType.integer())
                                                                        .then(Commands.argument("valueZ2", IntegerArgumentType.integer())
                                                                                .executes(CreateZoneCommand::createZone))))))))
                )
                // Comando para deletar uma zona existente
                .then(Commands.literal("delete")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .executes(CreateZoneCommand::deleteZone)))
                // Comando para listar todas as zonas
                .then(Commands.literal("list")
                        .executes(CreateZoneCommand::listZones))
                .then(Commands.literal("levels")
                        .then(Commands.argument("name", StringArgumentType.string())
                                .then(Commands.argument("minLevel", IntegerArgumentType.integer())
                                        .then(Commands.argument("maxLevel", IntegerArgumentType.integer())
                                                .executes(CreateZoneCommand::editZone)))))

        );
    }

    private static int editZone(CommandContext<CommandSourceStack> source) {

        String name = StringArgumentType.getString(source, "name");
        Integer minLevel = IntegerArgumentType.getInteger(source, "minLevel");
        Integer maxLevel = IntegerArgumentType.getInteger(source, "maxLevel");
        ZoneManager zoneManager = ZoneManager.getInstance();

        if (zoneManager.getZoneByName(name) != null) {
            Zone zone = zoneManager.getZoneByName(name);

            zone.setMinLevel(minLevel);
            zone.setMaxLevel(maxLevel);

            source.getSource().sendSystemMessage(Component.literal("Min level set to " + minLevel + " and Max level set to " + maxLevel + " in Zone: " + name));
            return 1;
        } else {
            source.getSource().sendSystemMessage(Component.literal("The zone " + name + " does not exist"));
            return 0;
        }

    }

    // Método para criar uma nova zona
    private static int createZone(CommandContext<CommandSourceStack> context) {
        String name = StringArgumentType.getString(context, "name");
        int valueX1 = IntegerArgumentType.getInteger(context, "valueX1");
        int valueY1 = IntegerArgumentType.getInteger(context, "valueY1");
        int valueZ1 = IntegerArgumentType.getInteger(context, "valueZ1");
        int valueX2 = IntegerArgumentType.getInteger(context, "valueX2");
        int valueY2 = IntegerArgumentType.getInteger(context, "valueY2");
        int valueZ2 = IntegerArgumentType.getInteger(context, "valueZ2");

        System.out.println("Parte um");

        ZoneManager zoneManager = ZoneManager.getInstance();
        zoneManager.addZone(new Zone(name, valueX1, valueY1, valueZ1, valueX2, valueY2, valueZ2));

        System.out.println("Parte 2");
        context.getSource().sendSystemMessage(Component.literal("Zone created: " + name));
        return 1;
    }

    // Método para deletar uma zona existente
    private static int deleteZone(CommandContext<CommandSourceStack> context) {
        String name = StringArgumentType.getString(context, "name");

        ZoneManager zoneManager = ZoneManager.getInstance();
        if (zoneManager.getZoneByName(name) != null) {
            zoneManager.removeZone(name);
            context.getSource().sendSystemMessage(Component.literal("Zone deleted: " + name));
            return 1;
        } else {
            context.getSource().sendFailure(Component.literal("Zone not found: " + name));
            return 0;
        }
    }

    // Método para listar todas as zonas
    private static int listZones(CommandContext<CommandSourceStack> context) {
        ZoneManager zoneManager = ZoneManager.getInstance();
        if (zoneManager.getZones().isEmpty()) {
            context.getSource().sendSystemMessage(Component.literal("No zones available."));
        } else {
            for (Zone zone : zoneManager.getZones()) {
                context.getSource().sendSystemMessage(Component.literal("Zone: " + zone.getName() + " [Levels: " + zone.getMinLevel() + "-" + zone.getMaxLevel() + "]"));
                context.getSource().sendSystemMessage(Component.literal("Coords: [ X:" + zone.getX1() + " Y: " + zone.getY1() + " Z: " + zone.getZ1() + "at X: " + zone.getX2() + "Y: " + zone.getY2() + "Z: " + zone.getZ2() + "]"));
            }
        }
        return 1;
    }
}
