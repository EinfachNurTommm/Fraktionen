package com.tom.fraktionen.commands;

import com.tom.fraktionen.Main;
import com.tom.fraktionen.handlers.MessageHandler;
import com.tom.fraktionen.sql.SQLMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatCommand implements CommandExecutor {

    private Main plugin;

    public ChatCommand(Main plugin) {
        this.plugin = plugin;
    }

    MessageHandler mm = new MessageHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(SQLMethods.getPlayerFaction(p.getName()) != null) {
            if(args.length > 0) {

                String faction = SQLMethods.getPlayerFaction(p.getName());

                String message = "";
                for (String arg : args) {
                    message = message + arg + " ";
                }
                message = message.trim();
                plugin.messageToAllMember(p, message);
            } else {
                p.sendMessage(mm.getMessage("Commands.FChat"));
            }
        } else {
            p.sendMessage(mm.getMessage("Commands.NoFaction"));
        }
        return false;
    }
}
