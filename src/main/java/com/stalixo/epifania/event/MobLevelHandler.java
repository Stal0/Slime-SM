package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import com.stalixo.epifania.config.ConfigManager;
import com.stalixo.epifania.config.world.WorldLevelMob;
import com.stalixo.epifania.particle.ModParticles;
import com.stalixo.epifania.util.MobStarRating;
import com.stalixo.epifania.zones.ZoneManager;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingConversionEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MobLevelHandler {

    static ZoneManager zoneManager = ZoneManager.getInstance();
    static ConfigManager configManager = EpifaniaRPG.configManager;

    @SubscribeEvent
    public static void onMobConvert(LivingConversionEvent.Post event) {
        LivingEntity originalEntity = event.getEntity();
        LivingEntity convertedEntity = event.getOutcome();

        // Verifica se a entidade original e a convertida são mobs
        if (originalEntity instanceof Mob oldMob && convertedEntity instanceof Mob newMob) {

            // Pega a Capability do mob original
            oldMob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(oldAttributes -> {
                // Pega a Capability do mob transformado e copia os atributos
                newMob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(newAttributes -> {
                    newAttributes.copyFrom(oldAttributes);
                });
            });
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

        }
    }


}