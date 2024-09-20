package com.stalixo.epifania.capability.mobCapability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class MobAttributesProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<MobAttributes> MOB_ATTRIBUTES = CapabilityManager.get(new CapabilityToken<MobAttributes>(){});

    private MobAttributes attributes = null;

    private final LazyOptional<MobAttributes> instance = LazyOptional.of(this::createMobAttributes);

    private MobAttributes createMobAttributes() {
        if (this.attributes == null) {
            this.attributes = new MobAttributes();
        }
        return this.attributes;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == MOB_ATTRIBUTES) {
            return instance.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createMobAttributes().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createMobAttributes().loadNBTData(compoundTag);
    }
}
