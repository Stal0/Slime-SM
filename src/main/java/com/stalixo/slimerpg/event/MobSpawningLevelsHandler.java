package com.stalixo.slimerpg.event;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MobSpawningLevelsHandler {

    @SubscribeEvent

    public static void onMobSpawn(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Monster) {
            Monster mob = (Monster) event.getEntity();
            Level level = event.getLevel();

            ResourceKey<Level> dimensionKey = level.dimension();


        }
    }
}
