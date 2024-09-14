package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.config.ConfigManager;
import com.stalixo.slimerpg.config.world.WorldLevelMob;
import com.stalixo.slimerpg.util.MobStarRating;
import com.stalixo.slimerpg.zones.ZoneManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

@Mod.EventBusSubscriber
public class MobLevelHandler {

    static ZoneManager zoneManager = ZoneManager.getInstance();
    static ConfigManager configManager = Slimerpg.configManager;

    @SubscribeEvent
    public static void onMobConvert(LivingConversionEvent.Post event) {
        LivingEntity originalEntity = event.getEntity();
        LivingEntity convertedEntity = event.getOutcome();

        if (originalEntity instanceof Mob oldMob && convertedEntity instanceof Mob newMob) {
            // Transfere o nível e a raridade do mob original para o mob transformado
            CompoundTag oldData = oldMob.getPersistentData();
            CompoundTag newData = newMob.getPersistentData();

            // Mantém os dados de nível e raridade
            int mobLevel = oldData.getInt("mobLevel");
            int starRating = oldData.getInt("starRating");

            // Copia os dados para a nova entidade
            newData.putInt("mobLevel", mobLevel);
            newData.putInt("starRating", starRating);

            System.out.println("Um mob se transformou com o nível " + mobLevel + " e raridade " + starRating);
            // Reaplica o nome com a classificação de estrelas e o nível
            MobStarRating.applyStarRating(newMob, mobLevel);

            // Atualiza os atributos de vida e ataque
            newMob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newMob.getMaxHealth() + mobLevel * 3);
            newMob.setHealth(newMob.getMaxHealth());
            newMob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + mobLevel * 1.5);
        }
    }

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

            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(mob.getMaxHealth() + mobLevel * 3);
            mob.setHealth(mob.getMaxHealth());

            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + mobLevel * 1.5);

        }
    }

}