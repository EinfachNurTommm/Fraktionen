package com.tom.fraktionen.sql;

import com.tom.fraktionen.Main;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SQLMethods {

    private static Main plugin;

    public SQLMethods(Main plugin) {
        SQLMethods.plugin = plugin;
    }

    /**
     * Create Faction
     * @param name
     * @param id
     * @param b
     * @param p
     * @param balance
     */
    public static void createFaction(String name, int id, String b, Player p, int balance) {

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
        Main.sql.update("INSERT INTO `Factions`(`Name`, `Id`, `Type`, `Owner`, `Balance`) VALUES ('" + name + "','" + id + "','" + b + "','" + p.getName() + "','" + balance + "')");
        Main.sql.update("INSERT INTO `Members`(`Name`, `Fraktion`, `ID`, `Rang`) VALUES ('" + p.getName() + "','" + name + "','" + id + "','6');");
        Main.sql.update("INSERT INTO `Ranks`(`Fraktion`) VALUES ('" + name + "');");
    }

    public static void deleteFaction(String name) {
        List<String> members;
        members = getFactionMember(name);
        for (String member : members) {
            removePlayerFaction(member);
        }
        Main.sql.update("DELETE FROM `Factions` WHERE `Name` = \"" + name + "\";");
    }


    public static List<String> getFactionList() {
        List<String> liste = new ArrayList<>();
        try {

            ResultSet rs = Main.sql.query("SELECT `Name` FROM `Factions`;");

            while (rs.next()) {
                liste.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }


    // Faction Member/Leader

    public static void setFactionLeader(String name, Player p) {
        Main.sql.update("UPDATE `Factions` SET `Owner`='" + p.getName() + "' WHERE `Name` = \"" + name + "\";");
        if(getPlayerFaction(p.getName()) == null) {
            Main.sql.update("INSERT INTO `Members`(`Name`, `Fraktion`, `ID`, `Rang`) VALUES ('" + p.getName() + "','" + name + "','" + getFactionId(name) + "','6');");
        } else {
            Main.sql.update("UPDATE `Members` SET `Fraktion`='" + name + "',`ID`='" + getFactionId(name) + "',`Rang`='6' WHERE `Name` = \"" + p.getName() + "\";");
        }
    }

    public static List<String> getFactionLeader(String name) {
        List<String> owners = new ArrayList<>();
        try {

            ResultSet rs = Main.sql.query("SELECT `Name` FROM `Members` WHERE `Fraktion` = \"" + name + "\" AND `Rang` >=5;");

            while (rs.next()) {
                owners.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return owners;
    }


    public static List<String> getFactionMember(String name) {
        List<String> members = new ArrayList<>();

        try {

            ResultSet rs = Main.sql.query("SELECT `Name` FROM `Members` WHERE `Fraktion` = \"" + name + "\" AND `Rang` < 5;");

            while (rs.next()) {
                members.add(rs.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }


    // Player Rank


    public static void setPlayerRank(String p, int rank) {
        Main.sql.update("UPDATE `Members` SET `Rang`='" + rank + "' WHERE `Name` = \"" + p + "\";");
    }

    public static int getPlayerRank(String name) {
        int i = -1;

        try {

            ResultSet rs = Main.sql.query("SELECT `Rang` FROM `Members` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                i = rs.getInt("Rang");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return i;
    }


    // Player Faction Methods

    public static void setPlayerFaction(String faction, Player p) {
        Main.sql.update("INSERT INTO `Members`(`Name`, `Fraktion`, `ID`, `Rang`) VALUES ('" + p.getName() + "','" + faction + "','" + getFactionId(faction) + "','0');");
    }

    public static void removePlayerFaction(String p) {
        Main.sql.update("DELETE FROM `Members` WHERE `Name` = \"" + p + "\";");
    }

    public static String getPlayerFaction(String p) {
        String s = null;

        try {

            ResultSet rs = Main.sql.query("SELECT `Fraktion` FROM `Members` WHERE `Name` = \"" + p + "\";");

            while (rs.next()) {
                s = rs.getString("Fraktion");
            }

            if(s == null || s.equals("")) {
                ResultSet rs1 = Main.sql.query("SELECT `Name` FROM `Factions` WHERE `Owner` = \"" + p + "\";");

                while (rs1.next()) {
                    s = rs1.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }


    // Get Faction Methods


    public static String getFactionId(String name) {
        String s = "";
        try {

            ResultSet rs = Main.sql.query("SELECT `Id` FROM `Factions` WHERE `Name` = \"" + name + "\";");

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

            ResultSet rs = Main.sql.query("SELECT `Name` FROM `Factions` WHERE `Id` = \"" + id + "\";");

            while (rs.next()) {
                s = rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return s;
    }


    public static int getFactionBalance(String name) {
        int balance = -1;

        try {

            ResultSet rs = Main.sql.query("SELECT `Balance` FROM `Factions` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                balance = rs.getInt("Balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return balance;
    }





    //Rank names

    public static void setRankName(String faction, int rank, String rankName) {
        Main.sql.update("UPDATE `Ranks` SET `" + rank + "`='" + rankName + "' WHERE `Fraktion` = \"" + faction + "\";");
    }

    public static String getRankName(String faction, int rank) {
        String rankName = null;

        try {

            ResultSet rs = Main.sql.query("SELECT `0`, `1`, `2`, `3`, `4`, `5`, `6` FROM `Ranks` WHERE `Fraktion` = \"" + faction + "\";");

            while (rs.next()) {
                rankName = rs.getString(rank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rankName;
    }


    // Exist Methods

    public static boolean factionExists(String name) {
        int i = -1;
        try {

            ResultSet rs = Main.sql.query("SELECT count(`Name`) FROM `Factions` WHERE `Name` = \"" + name + "\";");

            while (rs.next()) {
                //s = rs.getString("count(`Name`)");
                i = rs.getInt("count(`Name`)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }

        return i != 0;
    }

    public static boolean factionIdExists(int id) {
        int i = 0;
        boolean exist = true;
        try {

            ResultSet rs = Main.sql.query("SELECT `Id` FROM `Factions` WHERE `Id` = " + id + ";");

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
