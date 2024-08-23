package com.stalixo.slimerpg.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class PlayerAttributesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerAttributes> PLAYER_ATTRIBUTES = CapabilityManager.get(new CapabilityToken<PlayerAttributes>(){});

    private PlayerAttributes attributes = null;

    private final LazyOptional<PlayerAttributes> instance = LazyOptional.of(this::createPlayerAttributes);

    private PlayerAttributes createPlayerAttributes() {
        if (this.attributes == null) {
            this.attributes = new PlayerAttributes();
        }
        return this.attributes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == PLAYER_ATTRIBUTES) {
            return instance.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAttributes().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createPlayerAttributes().loadNBTData(compoundTag);
    }
}
