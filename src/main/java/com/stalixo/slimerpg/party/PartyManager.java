package com.stalixo.slimerpg.party;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class PartyManager {

    private static final Map<ServerPlayer, PartyPlayers> parties = new HashMap<>();
    private static final Map<ServerPlayer, ServerPlayer> invitations = new HashMap<>();

    public static void createParty(ServerPlayer leader) {
        if (!parties.containsKey(leader)) {
            parties.put(leader, new PartyPlayers(leader));
            leader.sendSystemMessage(Component.literal("Party created! You are the leader."));
        } else {
            leader.sendSystemMessage(Component.literal("You already have a party."));
        }
    }

    public static void invitePlayer(ServerPlayer leader, ServerPlayer invitee) {
        if (parties.containsKey(invitee)) {
            leader.sendSystemMessage(Component.literal(invitee.getName().getString() + " is already in a party."));
            return;
        }

        PartyPlayers party = parties.get(leader);
        if (party != null && party.getLeaderParty().equals(leader)) {
            invitations.put(invitee, leader);
            invitee.sendSystemMessage(Component.literal(leader.getName().getString() + " has invited you to join their party. Type /party accept " + leader.getName().getString() + " to join."));
            leader.sendSystemMessage(Component.literal("Invitation sent to " + invitee.getName().getString()));
        } else {
            leader.sendSystemMessage(Component.literal("You don't have a party or are not the leader."));
        }
    }

    public static void acceptInvitation(ServerPlayer player, ServerPlayer leader) {
        if (invitations.containsKey(player) && invitations.get(player).equals(leader)) {
            PartyPlayers party = parties.get(leader);
            if (party != null && party.addPlayer(player)) {
                parties.put(player, party); // Associate the player with the party
                invitations.remove(player); // Remove the invitation after it's accepted
            }
        } else {
            player.sendSystemMessage(Component.literal("No invitation from " + leader.getName().getString() + " found."));
        }
    }

    public static void listParty(ServerPlayer player) {
        PartyPlayers party = parties.get(player);
        if (party != null) {
            party.listParty(player);
        } else {
            player.sendSystemMessage(Component.literal("You are not in a party."));
        }
    }
}
