package com.tom.fraktionen.def;

import org.bukkit.entity.Player;

public class FactionInvite {

    private Player sender;

    private Player receiver;

    public FactionInvite(Player sender, Player receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public Player getSender() {
        return this.sender;
    }

    public Player getReceiver() {
        return this.receiver;
    }
}
