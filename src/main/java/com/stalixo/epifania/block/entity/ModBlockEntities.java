package com.stalixo.epifania.block.entity;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EpifaniaRPG.MODID);

    public static final RegistryObject<BlockEntityType<MasterAnvilBlockEntity>> ANVIL_BE =
            BLOCK_ENTITIES.register("anvil_be", () ->
        BlockEntityType.Builder.of(MasterAnvilBlockEntity::new,
                ModBlocks.ANVIL.get()).build(null));

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
