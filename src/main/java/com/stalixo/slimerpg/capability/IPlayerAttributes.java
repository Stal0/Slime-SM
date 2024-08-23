package com.stalixo.slimerpg.capability;

import com.stalixo.slimerpg.enums.Attributes;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IPlayerAttributes {
    int getAttribute(Attributes attribute);
    void setAttribute(Attributes attribute, int value);
}
