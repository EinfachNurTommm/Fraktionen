package com.tom.fraktionen;

import com.tom.fraktionen.commands.FactionCommand;
import com.tom.fraktionen.sql.MySQL;
import com.tom.fraktionen.sql.SQLMethods;
import org.bukkit.ChatColor;
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
        FactionCommand cmds = new FactionCommand(this);
        getCommand("fraktion").setExecutor(cmds);
    }


    private void registerMyEvents(){
       // new InventoryClickListener(this);
    }

    public String getPrefix() {
        return prefix;
    }
}
