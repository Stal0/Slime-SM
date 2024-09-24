package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.particle.ModParticles;
import com.stalixo.epifania.particle.custom.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpifaniaRPG.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.COMMON_PARTICLES.get(), CommonParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.UNCOMMON_PARTICLES.get(), UncommonParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.RARE_PARTICLES.get(), RareParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.EPIC_PARTICLES.get(), EpicParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.LEGENDARY_PARTICLES.get(), LegendaryParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.MYTHICAL_PARTICLES.get(), MythicalParticles.Provider::new);
    }
}
