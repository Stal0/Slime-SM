package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.Slimerpg;
import com.stalixo.slimerpg.capability.PlayerAttributes;
import com.stalixo.slimerpg.capability.PlayerAttributesProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Slimerpg.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerAttributesProvider.PLAYER_ATTRIBUTES).isPresent()) {
                event.addCapability(new ResourceLocation("slimerpg", "player_attributes"), new PlayerAttributesProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerAttributes.class);
    }

}
