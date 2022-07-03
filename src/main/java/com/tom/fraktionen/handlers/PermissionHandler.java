package com.tom.fraktionen.handlers;

import com.tom.fraktionen.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PermissionHandler {

    private final FileConfiguration configuration;

    public PermissionHandler() {
        File file = new File(JavaPlugin.getPlugin(Main.class).getDataFolder(), "Settings/Perms.yml");
        this.configuration = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("Permission.kick", 5);
        this.configuration.addDefault("Permission.invite", 5);
        this.configuration.addDefault("Permission.setrank", 5);
        this.configuration.addDefault("Permission.kasse.einzahlen", 0);
        this.configuration.addDefault("Permission.kasse.auszahlen", 5);
        this.configuration.addDefault("Permission.kasse.info", 4);
        FileHandler.save(file, this.configuration);
    }

    public int getMessage(String path) {
        return this.configuration.getInt(path);
    }

}
