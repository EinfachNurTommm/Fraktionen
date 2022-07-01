package com.tom.fraktionen.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MemberCommand implements CommandExecutor {

    // /memberinfo
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        // Playername, Fraktion, Rang
        return false;
    }
}
