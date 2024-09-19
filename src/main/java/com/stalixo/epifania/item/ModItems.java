package com.stalixo.epifania.item;

import com.stalixo.epifania.EpifaniaRPG;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EpifaniaRPG.MODID);

    public static final RegistryObject<Item> HYBERNIUM_ORE = ITEMS.register("hybernium_ore",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HYBERNIUM_INGOT = ITEMS.register("hybernium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> HYBERNIUM_SWORD = ITEMS.register("hybernium_sword",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
