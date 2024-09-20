package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.capability.mobCapability.MobAttributesProvider;
import com.stalixo.epifania.particle.ModParticles;
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

        // Verifica se é um Mob específico
        if (entity instanceof Mob) {
            Mob mob = (Mob) entity;

            // Usa a Capability para acessar os atributos do mob
            mob.getCapability(MobAttributesProvider.MOB_ATTRIBUTES).ifPresent(mobAttributes -> {

                // Verifica se o cooldown chegou a 0
                if (mobAttributes.getParticleCooldown() <= 0) {
                    // Reseta o cooldown
                    mobAttributes.setParticleCooldown(COOLDOWN_TIME);

                    int rarity = mobAttributes.getRarity();
                    int level = mobAttributes.getMobLevel();

                    // Se não houver raridade, não spawna partículas
                    if (rarity <= 0) {
                        return;
                    }

                    // Spawna as partículas de acordo com a raridade
                    spawnParticlesAroundMob(mob, rarity);
                    System.out.println(rarity + " " + level + " MobParticle" + mob.getType().toString());
                } else {
                    // Reduz o cooldown a cada tick
                    mobAttributes.setParticleCooldown(mobAttributes.getParticleCooldown() - 1);
                }
            });
        }
    }

    // Função que spawnará as partículas ao redor do mob
    private static void spawnParticlesAroundMob(Mob mob, int starRating) {

        for (int i = 0; i < 10; i++) {
            double offsetX = (mob.level().random.nextDouble() - 0.5D) * 2.0D;
            double offsetY = mob.level().random.nextDouble() * mob.getBbHeight();
            double offsetZ = (mob.level().random.nextDouble() - 0.5D) * 2.0D;

            // Spawna as partículas com base na raridade
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
