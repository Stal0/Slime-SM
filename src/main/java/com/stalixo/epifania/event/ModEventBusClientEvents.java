package com.stalixo.epifania.event;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.block.entity.AnvilBlockEntity;
import com.stalixo.epifania.block.entity.ModBlockEntities;
import com.stalixo.epifania.block.entity.renderer.AnvilBlockEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpifaniaRPG.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.ANVIL_BE.get(), AnvilBlockEntityRenderer::new);
    }
}
