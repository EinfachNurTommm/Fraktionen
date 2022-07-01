package com.tom.fraktionen.sql;

import com.tom.fraktionen.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SQLMethods {

    private static Main plugin;

    public SQLMethods(Main plugin) {
        this.plugin = plugin;
    }


    public static boolean createFaction(String name, int id, String b, Player p, int balance) {

        /*
        DataManager dataManager = new DataManager();
        StarvalPlayer starvalPlayer = dataManager.getStarvalPlayer(p);
        Faction.FactionType factionType;

        if(b == "g") { // good
            factionType = Faction.FactionType.GOOD;
        } else if(b == "n") { // neutral
            factionType = Faction.FactionType.NEUTRAL;
        } else if(b == "b") { // bad
            factionType = Faction.FactionType.BAD;
        } else {
            return false;
        }

        Faction fa = new Faction(name, id, factionType, starvalPlayer, balance);
        */
        plugin.sql.update("INSERT INTO `Factions`(`Name`, `Id`, `Type`, `Owner`, `Balance`) VALUES ('" + name + "','" + id + "','" + b + "','" + p.getName() + "','" + balance + "')");
        return true;
    }

    public static boolean deleteFaction(String name) {
        plugin.sql.update("DELETE FROM `Factions` WHERE `Name` = \"" + name + "\";");
        return true;
    }


    public static List<String> getFactionList() {
        List<String> liste = new ArrayList<>();
        String s;
        //Faction fa = null;
        try {

            ResultSet rs = plugin.sql.query("SELECT `Name` FROM `Factions`;");

            while (rs.next()) {
                liste.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }


    public static boolean setFactionLeader(String name, Player p) {
        plugin.sql.update("UPDATE `Factions` SET `Owner`='" + p.getName() + "' WHERE `Name` = \"" + name + "\";");
        return true;
    }


    public static String getFactionLeader(String name) {
        String s = "";
        try {

            ResultSet rs = plugin.sql.query("SELECT `Owner` FROM `Factions` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                s = rs.getString("Owner");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }


    public static List<String> getFactionMember(String name) {
        List<String> members = new ArrayList<>();

        try {

            ResultSet rs = plugin.sql.query("SELECT `Name`, `Rang` FROM `Members` WHERE `Fraktion` = \"" + name + "\";");

            while (rs.next()) {
                members.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }


    public static boolean setPlayerFaction(String faction, Player p) {
        plugin.sql.update("INSERT INTO `Members`(`Name`, `Fraktion`, `ID`, `Rang`) VALUES ('" + p.getName() + "','" + faction + "','" + getFactionId(faction) + "','0');");
        return true;
    }

    public static boolean removePlayerFaction(Player p) {
        plugin.sql.update("DELETE FROM `Members` WHERE `Name` = \"" + p.getName() + "\";");
        return true;
    }


    public static int getPlayerRank(String name) {
        int i = -1;

        try {

            ResultSet rs = plugin.sql.query("SELECT `Rang` FROM `Members` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                i = rs.getInt("Rang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }


    public static String getPlayerFaction(String p) {
        String s = null;

        try {

            ResultSet rs = plugin.sql.query("SELECT `Fraktion` FROM `Members` WHERE `Name` = \"" + p + "\";");

            while (rs.next()) {
                s = rs.getString("Fraktion");
            }

            if(s == null || s.equals("")) {
                ResultSet rs1 = plugin.sql.query("SELECT `Name` FROM `Factions` WHERE `Owner` = \"" + p + "\";");

                while (rs1.next()) {
                    s = rs1.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }



    public static String getFactionId(String name) {
        String s = "";
        try {

            ResultSet rs = plugin.sql.query("SELECT `Id` FROM `Factions` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                s = rs.getString("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }

    public static String getFactionName(int id) {
        String s = "";
        try {

            ResultSet rs = plugin.sql.query("SELECT `Name` FROM `Factions` WHERE `Id` = \"" + id + "\";");

            while (rs.next()) {
                s = rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }


    public static boolean factionExists(String name) {
        String s = "";
        int i = -1;
        boolean exist = true;
        try {

            ResultSet rs = plugin.sql.query("SELECT count(`Name`) FROM `Factions` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                //s = rs.getString("count(`Name`)");
                i = rs.getInt("count(`Name`)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        if(i == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean factionIdExists(int id) {
        int i = 0;
        boolean exist = true;
        try {

            ResultSet rs = plugin.sql.query("SELECT `Id` FROM `Factions` WHERE `Id` = " + id + ";");

            while (rs.next()) {
                i = rs.getInt("Id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!(i > 0)) {
            exist = false;
        }
        return exist;
    }


    public static int getRandomFactionID() {

        Random random = new Random();
        int id = random.nextInt(1000);

        while(factionIdExists(id)) {
            id = random.nextInt(1000);
        }

        return id;
    }





}
