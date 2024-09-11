package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.config.ConfigManager;
import com.stalixo.slimerpg.config.world.WorldLevelMob;
import com.stalixo.slimerpg.util.MobStarRating;
import com.stalixo.slimerpg.zones.ZoneManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class MobLevelHandler {

    static ZoneManager zoneManager = ZoneManager.getInstance();
    static ConfigManager configManager = Slimerpg.configManager;

    @SubscribeEvent
    public static void onMobSpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Monster mob) {

            Level level = event.getLevel();
            String dimensionName = level.dimension().location().toString();

            WorldLevelMob mobWorld = configManager.getWorldLevelByDimension(dimensionName);
            int mobLevel = zoneManager.getMobLevel(mob);

            if (mobLevel != 0) {
                mobLevel = mobWorld.getLevelByDistance(mob);
            }

            MobStarRating.applyStarRating(mob, mobLevel);
            CompoundTag data = mob.getPersistentData();
            data.putInt("mobLevel", mobLevel);

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0 + mobLevel * 3);
            mob.setHealth(mob.getMaxHealth());

            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + mobLevel * 1.5);

        }
    }

}