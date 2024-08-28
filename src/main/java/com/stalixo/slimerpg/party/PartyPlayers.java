package com.stalixo.slimerpg.party;

import net.minecraft.server.level.ServerPlayer;

public class PartyPlayers {

    ServerPlayer[] players = new ServerPlayer[4];
    ServerPlayer leaderParty;

    public PartyPlayers(ServerPlayer leaderParty) {
        this.leaderParty = leaderParty;
        players[0] = leaderParty;
    }

    public ServerPlayer getLeaderParty() {
        return leaderParty;
    }

    public void setLeaderParty(ServerPlayer leaderParty) {
        this.leaderParty = leaderParty;
    }

    public void addPlayer(ServerPlayer player) {

    }

}
