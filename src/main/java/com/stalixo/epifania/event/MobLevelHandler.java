package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
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
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MobLevelHandler {

    static ZoneManager zoneManager = ZoneManager.getInstance();
    static ConfigManager configManager = EpifaniaRPG.configManager;

    private static final int COOLDOWN_TIME = 100;

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

            CompoundTag data = mob.getPersistentData();

            int starRating = data.getInt("starRating");


            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(mob.getMaxHealth() + mobLevel * 3);
            mob.setHealth(mob.getMaxHealth());

            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + mobLevel * 1.5);

        }
    }

    @SubscribeEvent
    public static void onMobTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        // Verifica se é um Mob específico, pode ser Monster, Creeper, etc.
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;

            // Adiciona o cooldown no NBT da entidade (ou use uma variável customizada)
            if (mob.getPersistentData().getInt("particleCooldown") <= 0) {
                // Reseta o cooldown
                mob.getPersistentData().putInt("particleCooldown", COOLDOWN_TIME);

                int starRating = mob.getPersistentData().getInt("starRating");

                // Spawna as partículas
                spawnParticlesAroundMob(mob, starRating);
            } else {
                // Reduz o cooldown a cada tick
                mob.getPersistentData().putInt("particleCooldown", mob.getPersistentData().getInt("particleCooldown") - 1);
            }
        }
    }

    // Função que spawnará as partículas ao redor do mob
    private static void spawnParticlesAroundMob(Mob mob, int starRating) {
        for (int i = 0; i < 10; i++) {
            double offsetX = (mob.level().random.nextDouble() - 0.5D) * 2.0D;
            double offsetY = mob.level().random.nextDouble() * mob.getBbHeight();
            double offsetZ = (mob.level().random.nextDouble() - 0.5D) * 2.0D;

            switch (starRating) {
                case 1:
                    mob.level().addParticle(ModParticles.COMMON_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                case 2:
                    mob.level().addParticle(ModParticles.UNCOMMON_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                case 3:
                    mob.level().addParticle(ModParticles.RARE_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                case 4:
                    mob.level().addParticle(ModParticles.EPIC_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                case 5:
                    mob.level().addParticle(ModParticles.LEGENDARY_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                case 6:
                    mob.level().addParticle(ModParticles.MYTHICAL_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
                default:
                    mob.level().addParticle(ModParticles.COMMON_PARTICLES.get(), mob.getX() + offsetX, mob.getY() + offsetY, mob.getZ() + offsetZ, 0.0D, 0.0D, 0.0D);
                    break;
            }


        }
    }

}