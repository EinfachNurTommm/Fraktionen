package com.tom.fraktionen;

import com.tom.fraktionen.commands.ChatCommand;
import com.tom.fraktionen.commands.FactionCommand;
import com.tom.fraktionen.handlers.MessageHandler;
import com.tom.fraktionen.sql.MySQL;
import com.tom.fraktionen.sql.SQLMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private String prefix = "§7[§cFraktionen§7] ";
    public String noPerm = prefix + " &cUnzureichende Rechte!";
    public int startBalance = 1000;

    public SQLMethods mySQL = new SQLMethods(this);

    public static MySQL sql;

    @Override
    public void onEnable() {
        registerMyEvents();
        registerMyCommands();

        sql = new MySQL("univastro.net", "starvalcity_dev_factions", "starvalcity_dev", "KDLJ5WBNRO5GsjWD");

        System.out.println("=============================================================================");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println(ChatColor.GREEN + "Starte " + ChatColor.YELLOW + "Fraktionssystem...");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("=============================================================================");

    }

    @Override
    public void onDisable() {
        System.out.println("=============================================================================");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println(ChatColor.RED + "Stoppe " + ChatColor.YELLOW + "Fraktionssystem...");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("                                                                             ");
        System.out.println("=============================================================================");
    }


    public void registerMyCommands(){
        getCommand("fraktion").setExecutor(new FactionCommand(this));

        getCommand("f").setExecutor(new ChatCommand(this));
    }


    private void registerMyEvents(){
       // new InventoryClickListener(this);
    }

    public String getPrefix() {
        return prefix;
    }

    public void messageToAllMember(Player p, String message) {
        MessageHandler mm = new MessageHandler();
        String faction = SQLMethods.getPlayerFaction(p.getName());
        int rank = SQLMethods.getPlayerRank(p.getName());
        String rankName = SQLMethods.getRankName(faction, rank);

        for(Player all : Bukkit.getOnlinePlayers()) {
            if(SQLMethods.getPlayerFaction(all.getName()) != null) {
                if(SQLMethods.getPlayerFaction(all.getName()).equalsIgnoreCase(faction)) {
                    all.sendMessage(mm.getMessage("Commands.MessageFChatFormat")
                            .replace("%rankname%", rankName)
                            .replace("%name%", p.getName())
                            .replace("%message%", message));
                }
            }
        }
    }
}
