package com.stalixo.epifania.datagen;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EpifaniaRPG.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.HYBERNIUM_INGOT);
        simpleItem(ModItems.HYBERNIUM_ORE);

        simpleItem(ModItems.PENCIL);
        simpleItem(ModItems.HYBERNIUM_SWORD);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(EpifaniaRPG.MODID, "item/" + item.getId().getPath()));
    }
}
