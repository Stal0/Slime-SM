package com.stalixo.epifania.screen;

import com.stalixo.epifania.EpifaniaRPG;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, EpifaniaRPG.MODID);

    public static final RegistryObject<MenuType<AnvilMenu>> ANVIL_MENU =
            registerMenyType("anvil_menu", AnvilMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenyType(String name, IContainerFactory<T> factory) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus bus) {
        MENUS.register(bus);
    }
}
