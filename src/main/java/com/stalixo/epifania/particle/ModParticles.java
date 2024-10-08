package com.stalixo.epifania.particle;

import com.stalixo.epifania.EpifaniaRPG;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EpifaniaRPG.MODID);

    public static final RegistryObject<SimpleParticleType> COMMON_PARTICLES =
            PARTICLE.register("common_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> UNCOMMON_PARTICLES =
            PARTICLE.register("uncommon_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> RARE_PARTICLES =
            PARTICLE.register("rare_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> EPIC_PARTICLES =
            PARTICLE.register("epic_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> LEGENDARY_PARTICLES =
            PARTICLE.register("legendary_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> MYTHICAL_PARTICLES =
            PARTICLE.register("mythical_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus bus) {
        PARTICLE.register(bus);
    }
}
