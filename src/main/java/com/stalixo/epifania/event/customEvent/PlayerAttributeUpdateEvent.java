package com.stalixo.epifania.event.customEvent;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public class PlayerAttributeUpdateEvent extends Event {

    private final ServerPlayer player;

    public PlayerAttributeUpdateEvent (ServerPlayer player) {
        this.player = player;
    }

    public ServerPlayer getPlayer() {
        return player;
    }
}
