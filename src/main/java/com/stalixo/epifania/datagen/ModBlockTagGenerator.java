package com.stalixo.epifania.datagen;

import com.stalixo.epifania.EpifaniaRPG;
import com.stalixo.epifania.block.ModBlocks;
import io.redspace.ironsspellbooks.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {

    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, EpifaniaRPG.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

         //Tag para informar que a ferramenta para a mineração do bloco é a picareta.
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.ANVIL.get());

        //Tag para informar o nivel da ferramenta para a mineração.
        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.ANVIL.get());

    }
}
