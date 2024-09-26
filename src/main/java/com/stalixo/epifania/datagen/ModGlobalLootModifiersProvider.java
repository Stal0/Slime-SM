package com.stalixo.epifania.datagen;

import com.stalixo.epifania.EpifaniaRPG;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class ModGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifiersProvider(PackOutput output) {
        super(output, EpifaniaRPG.MODID);
    }

    @Override
    protected void start() {

    }
}
