package com.stalixo.slimerpg.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = "slimerpg", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModMenuTypes {

    // Cria um DeferredRegister para gerenciar registros de MenuType
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "slimerpg");

    // Registra o MenuType para o AttributeMenu
    public static final RegistryObject<MenuType<PlayerAttributeMenu>> ATTRIBUTE_MENU = MENU_TYPES.register("attribute_menu",
            () -> IForgeMenuType.create((windowId, inv, data) -> new PlayerAttributeMenu(windowId, inv, data)));

    // Método para registrar o DeferredRegister no Event Bus
    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
