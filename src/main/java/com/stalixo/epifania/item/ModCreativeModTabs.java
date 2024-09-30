package com.stalixo.epifania.item;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EpifaniaRPG.MODID);

    public static final RegistryObject<CreativeModeTab> EPIFANIA_TAB = CREATIVE_MOD_TABS.register("epifania_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.HYBERNIUM_ORE.get()))
                    .title(Component.translatable("creativetab.epifania_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.HYBERNIUM_ORE.get());
                        pOutput.accept(ModItems.HYBERNIUM_INGOT.get());
                        pOutput.accept(ModItems.HYBERNIUM_SWORD.get());
                        pOutput.accept(ModItems.PENCIL.get());
                        pOutput.accept(ModItems.RUBY.get());

                        pOutput.accept(ModBlocks.ANVIL.get());
                    })
                    .build());

    public static void register(IEventBus modEventBus) {
        CREATIVE_MOD_TABS.register(modEventBus);
    }
}
