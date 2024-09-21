package com.stalixo.epifania.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
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
        dispatcher.register(Commands.literal("invoke")
                .then(Commands.argument("mobName", StringArgumentType.string())
                        .then(Commands.argument("rarity", StringArgumentType.string())
                                .then(Commands.argument("level", IntegerArgumentType.integer())
                                        .executes(InvokeCommand::spawnMobCustom)))));
    }

    // Função para executar o comando de spawn de mob customizado
    private static int spawnMobCustom(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        // Verifica se o comando foi executado por um jogador
        if (source.getEntity() instanceof ServerPlayer player) {
            Level world = player.level();
            BlockPos playerPos = player.blockPosition(); // Posição do jogador

            // Obtém os argumentos
            String mobName = StringArgumentType.getString(context, "mobName");
            String rarity = StringArgumentType.getString(context, "rarity");
            int level = IntegerArgumentType.getInteger(context, "level");

            // Converte o nome do mob em um EntityType válido
            EntityType<?> entityType = EntityType.byString(mobName).orElse(null);

            // Verifica se o mob existe
            if (entityType != null && entityType.create(world) instanceof Mob mob) {
                // Define a posição do mob (na frente do jogador)
                mob.moveTo(playerPos.getX() + 2, playerPos.getY(), playerPos.getZ(), 0, 0);

                // Spawna o mob no mundo
                world.addFreshEntity(mob);

                // Aplica o nível e a raridade através da capability
                applyMobLevelAndRarity(mob, rarity, level);

                // Retorna sucesso
                return 1;
            } else {
                // Envia uma mensagem de erro se o mob não foi encontrado
                source.sendFailure(Component.literal("Mob '" + mobName + "' não encontrado!"));
                return 0;
            }
        }

        return 0;
    }

    // Função para aplicar o nível e a raridade ao mob recém-spawnado usando capabilities
    private static void applyMobLevelAndRarity(Mob mob, String rarity, int level) {
        int starRating;

        // Converter string da raridade para número de estrelas
        switch (rarity.toLowerCase()) {
            case "common":
                starRating = 1;
                break;
            case "uncommon":
                starRating = 2;
                break;
            case "rare":
                starRating = 3;
                break;
            case "epic":
                starRating = 4;
                break;
            case "legendary":
                starRating = 5;
                break;
            case "mythical":
                starRating = 6;
                break;
            default:
                starRating = 1; // Se a raridade for inválida, use o valor "common"
                break;
        }

        // Aplica os valores de nível e raridade na Capability do mob
        mob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(mobAttributes -> {
            mobAttributes.setMobLevel(level);  // Define o nível
            mobAttributes.setRarity(starRating);  // Define a raridade

        });
    }

}
