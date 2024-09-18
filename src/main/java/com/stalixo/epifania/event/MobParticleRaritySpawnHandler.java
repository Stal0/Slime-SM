package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.particle.ModParticles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpifaniaRPG.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class MobParticleRaritySpawnHandler {

    private static final int COOLDOWN_TIME = 100;

    @SubscribeEvent
    public static void onMobTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        // Verifica se é um Mob específico, pode ser Monster, Creeper, etc.
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;

            CompoundTag data = mob.getPersistentData();

            // Adiciona o cooldown no NBT da entidade (ou use uma variável customizada)
            if (data.getInt("particleCooldown") <= 0) {
                // Reseta o cooldown
                data.putInt("particleCooldown", COOLDOWN_TIME);

                int starRating = data.getInt("starRating");

                if (starRating <= 0) {
                    return; // Sem raridade, não spawna partículas
                }

                // Spawna as partículas
                spawnParticlesAroundMob(mob, starRating);
            } else {
                // Reduz o cooldown a cada tick
                data.putInt("particleCooldown", mob.getPersistentData().getInt("particleCooldown") - 1);
            }
        }
    }

    // Função que spawnará as partículas ao redor do mob
    private static void spawnParticlesAroundMob(Mob mob, int starRating) {

        for (int i = 0; i < 10; i++) {
            double offsetX = (mob.level().random.nextDouble() - 0.5D) * 2.0D;
            double offsetY = mob.level().random.nextDouble() * mob.getBbHeight();
            double offsetZ = (mob.level().random.nextDouble() - 0.5D) * 2.0D;

            System.out.println(starRating + " " + mob.getPersistentData().getInt("mobLevel") + " " + mob.getType().toString());

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
            }


        }
    }
}
