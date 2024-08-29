package com.stalixo.slimerpg.party;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.List;

public class PartyPlayers {

    private static final int MAX_PLAYERS = 4;
    private List<ServerPlayer> players = new ArrayList<>();
    private ServerPlayer leaderParty;

    public PartyPlayers(ServerPlayer leaderParty) {
        this.leaderParty = leaderParty;
        this.players.add(leaderParty);
    }

    public ServerPlayer getLeaderParty() {
        return leaderParty;
    }

    public void setLeaderParty(ServerPlayer leaderParty) {
        this.leaderParty = leaderParty;
    }

    public boolean addPlayer(ServerPlayer player) {
        if (players.size() < MAX_PLAYERS) {
            players.add(player);
            notifyParty(Component.literal(player.getName().getString() + " has joined the party!"));
            return true;
        } else {
            leaderParty.sendSystemMessage(Component.literal("The party is full! Cannot add more players."));
            return false;
        }
    }

    public void removePlayer(ServerPlayer player) {
        if (players.remove(player)) {
            notifyParty(Component.literal(player.getName().getString() + " has left the party!"));
        }
    }

    public void listParty(ServerPlayer player) {
        player.sendSystemMessage(Component.literal("Party List:"));
        for (ServerPlayer partyMember : players) {
            if (partyMember != null) {
                player.sendSystemMessage(Component.literal(partyMember.getName().getString()));
            }
        }
    }

    private void notifyParty(Component message) {
        for (ServerPlayer partyMember : players) {
            if (partyMember != null) {
                partyMember.sendSystemMessage(message);
            }
        }
    }
}
