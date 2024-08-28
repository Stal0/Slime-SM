package com.stalixo.slimerpg.party;

import net.minecraft.network.chat.Component;
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
        for (int i = 0; i <= players.length; i++) {
            if (players[i] == null) {
                players[i] = player;
                return;
            }
        }
    }

    public void removePlayer(ServerPlayer player) {
        for (int i = 0; i <= players.length; i++) {
            if (player == players[i]) {
                players[i] = null;
                return;
            }
        }
    }

    public void listParty(ServerPlayer player) {
        player.sendSystemMessage(Component.literal("Party List:"));
        for (ServerPlayer player1 : players) {
            player.sendSystemMessage(Component.literal(player1.getName().getString()));
        }
    }

}
