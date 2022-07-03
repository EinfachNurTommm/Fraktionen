package com.tom.fraktionen.handlers;

import com.tom.fraktionen.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class MessageHandler {

    private final FileConfiguration configuration;

    public MessageHandler() {
        File file = new File(JavaPlugin.getPlugin(Main.class).getDataFolder(), "Settings/Messages.yml");
        this.configuration = (FileConfiguration) YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        this.configuration.addDefault("Prefix", "&7[&cFraktionen&7]");
        this.configuration.addDefault("Commands.NoPermission", "%prefix% &cUnzureichende Rechte!");
        this.configuration.addDefault("Commands.DisplayCreate", "%prefix% /Fraktion create [name] [type] | &eErstellt eine Fraktion");
        this.configuration.addDefault("Commands.DisplayInvite", "%prefix% /faction invite [Spieler] | &eLädt einen Spieler in eine Fraktion ein");
        this.configuration.addDefault("Commands.DisplayAccept", "%prefix% /faction accept [Spieler] | &eAkzeptiert eine Fraktionseinladung");
        this.configuration.addDefault("Commands.DisplayKick", "%prefix% /faction kick [Spieler] | &eKickt einen Spieler aus einer Fraktion");
        this.configuration.addDefault("Commands.DisplayInfo", "%prefix% /faction info [Name] | &eZeigt Informationen einer Fraktion");
        this.configuration.addDefault("Commands.DisplayDelete", "%prefix% /faction delete [Name] | &eEntfernt eine Fraktion");
        this.configuration.addDefault("Commands.DisplayList", "%prefix% /faction list [Name]| &eListet alle Fraktionsmitglieder auf");
        this.configuration.addDefault("Commands.Reload", "%prefix% /faction reload | &eLädt die Konfiguration neu");
        this.configuration.addDefault("Commands.SetLeader", "%prefix% /faction setLeader [Name] [Spieler] | &eSetzt einen neuen Leader");
        this.configuration.addDefault("Commands.Reset", "%prefix% /faction reset [Name] | &eSetzt eine Fraktion zurück");
        this.configuration.addDefault("Commands.SetRank", "%prefix% /faction setRank [Spieler] [Rang] | &eSetzt einem Spieler einen Rang");
        this.configuration.addDefault("Commands.Kasse", "%prefix% /faction kasse einzahlen/auszahlen/info | &eUm die Fraktionskasse zu verwalten");
        this.configuration.addDefault("Commands.FChat", "%prefix% /f [Nachricht] | &eSchreibe im Fraktionschat");

        this.configuration.addDefault("Commands.NoFaction", "%prefix% &cDu bist in keiner Fraktion!");

        this.configuration.addDefault("Commands.MessageReload", "%prefix% &aLade Konfiguration ...");
        this.configuration.addDefault("Commands.MessageReloadComplete", "%prefix% &aLadevorgang abgeschlossen!");
        this.configuration.addDefault("Commands.MessageTopStats", "%prefix% &c%id%.  &eFraktion: &7%faction% &8| &eKills: &7%kills%");

        this.configuration.addDefault("Commands.MessageDeleteFaction", "%prefix% &9Fraktion &e%faction% &9wurde entfernt!");
        this.configuration.addDefault("Commands.MessageDeleteFactionFailed", "%prefix% &cNur der Manager darf diesen Befehl ausführen!");

        this.configuration.addDefault("Commands.MessageLeaveFaction", "%prefix% &9Du hast die Fraktion verlassen!");

        this.configuration.addDefault("Commands.MessageCreateFailedExist", "%prefix% &cFraktion &e%faction% &cexistiert bereits!");
        this.configuration.addDefault("Commands.MessageCreateFailedType", "%prefix% &cDer gesetzte Fraktionstyp existiert nicht!");
        this.configuration.addDefault("Commands.MessageCreateNameMatcherTriggered", "%prefix% &cDieser Name kann nicht gesetzt werden!");
        this.configuration.addDefault("Commands.MessageCreateFactionSuccess", "%prefix% &aFraktion &e%faction% &awurde erstellt!");

        this.configuration.addDefault("Commands.MessageInviteOffline", "%prefix% &cSpieler &e%name% &cist nicht verfügbar!");
        this.configuration.addDefault("Commands.MessageInviteAlreadyReceived", "%prefix% &e%name% &chat bereits eine Einladung erhalten!");
        this.configuration.addDefault("Commands.MessageInviteSendFaction", "%prefix% &e%name% &9wurde in die Fraktion eingeladen!");
        this.configuration.addDefault("Commands.MessageInviteSendOwner", "%prefix% &e%name% &9hat dich zur Fraktion &7(&e%faction%&7)&9 eingeladen!");
        this.configuration.addDefault("Commands.MessageInviteFailed", "%prefix% &cDer Spieler &e%name% &cbefindet sich bereits in einer Fraktion!");

        this.configuration.addDefault("Commands.MessageAcceptFactionInvite", "%prefix% &e%name% &ahat deine Einladung akzeptiert!");
        this.configuration.addDefault("Commands.MessageAcceptFactionPlayer", "%prefix% &aDu bist der Fraktion &e%faction%&a beigetreten!");
        this.configuration.addDefault("Commands.MessageAcceptError", "%prefix% &cKritischer Fehler. Bitte melde diesen Fehler einem Developer!");
        this.configuration.addDefault("Commands.MessageAcceptFailed", "%prefix% &e%name% &cbefindet sich in keiner Fraktion!");
        this.configuration.addDefault("Commands.MessageAcceptNoInvite", "%prefix% &cDu hast keine Einladung von &e%name%&c erhalten!");

        this.configuration.addDefault("Commands.MessageDenyFactionInvite", "%prefix% &e%name% &chat deine Einladung abgelehnt!");
        this.configuration.addDefault("Commands.MessageDenyFactionPlayer", "%prefix% &cDu hast die einladung in die Fraktion &e%faction%&c abgelehnt!");

        this.configuration.addDefault("Commands.MessageSetLeaderFailed", "%prefix% &cDer Spieler &e%name% &cexistiert nicht!");
        this.configuration.addDefault("Commands.MessageSetLeaderSuccess", "%prefix% &aDer Spieler &e%name% &aist nun Leader der Fraktion §e%faction%!");

        this.configuration.addDefault("Commands.MessageKickSuccess", "%prefix% &e%name% &9kickte dich aus der Fraktion &e%clan%&9!");
        this.configuration.addDefault("Commands.MessageKickRemoved", "%prefix% &e%name% &9wurde aus deiner Fraktion entfernt!");
        this.configuration.addDefault("Commands.MessageKickFailed", "%prefix% &cSpieler &e%name% &ckonnte nicht von der Fraktion entfernt werden!");

        this.configuration.addDefault("Commands.MessageSetRankSuccess", "%prefix% &aDu hast &e%name% &aden Rang §e%rank% §azugewiesen!");
        this.configuration.addDefault("Commands.MessageSetRankPlayer", "%prefix% &aDu wurdest von §e%name% &adem Rang §e%rang% §azugewiesen!");
        this.configuration.addDefault("Commands.MessageSetRankFailed", "%prefix% &e%name% &cbefindet sich nicht in dieser Fraktion!");
        this.configuration.addDefault("Commands.MessageSetRankHigherRank", "%prefix% &cDu kannst §e%name% §ckeinen Rang zuweisen!");
        this.configuration.addDefault("Commands.MessageSetRankNumberBetween", "%prefix% &cDu kannst nur den Rang 0-5 setzten!");
        this.configuration.addDefault("Commands.MessageSetRankMustNumber", "%prefix% &cDer angegebene Rang muss eine Zahl sein!");

        this.configuration.addDefault("Commands.MessageFChatFormat", "&8[&1Funk&8]&3 &b%rankname% &3%name% &7: %message%");

        this.configuration.addDefault("Commands.MessageListHeader", "&6-=-=-=-&e&lFraktion&6=-=&e&lListe&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageListNoFactions", "%prefix% &cMomentan Existieren keine Fraktionen!");
        this.configuration.addDefault("Commands.MessageInfoHeader", "&6-=-=-=-&e&lFraktion&6=-=&e&lInfo&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageInfoNoMember", "%prefix% &cDiese Fraktion hat keine Member!");
        this.configuration.addDefault("Commands.MessageStatsHeader", "&6-=-=-=-&e&lFraktion&6=-=&e&lStatistik&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageStatsSpace", "");
        this.configuration.addDefault("Commands.MessageStatsFactionName", "&a&lFraktionsname&7: %faction%");
        this.configuration.addDefault("Commands.MessageStatsFactionKills", "&a&lKills&7: &e%kills%");
        this.configuration.addDefault("Commands.MessageStatsFactionMembers", "&a&lMitglied(er)&7: %members%");
        this.configuration.addDefault("Commands.MessageStatsFooter", "&6-=-=-=-&e&lFraktion&6=-=&e&lStatistik&6-=-=-=-");
        this.configuration.addDefault("Commands.MessageNoFactionExist", "%prefix% &cDiese Fraktion existiert nicht!");
        this.configuration.addDefault("Commands.MessageNoFactionOwner", "%prefix% &cDu bist nicht Manager der Fraktion!");
        this.configuration.addDefault("Commands.MessageAlreadyInFaction", "%prefix% &cDu bist bereits in einer Fraktion!");
        this.configuration.addDefault("Commands.MessageNoFaction", "%prefix% &cDu bist in keiner Fraktion!");
        this.configuration.addDefault("Commands.MessageFactionNameBlacklisted", "%prefix% &cDer eingegebene Name ist nicht verfügbar.");
        FileHandler.save(file, this.configuration);
    }

    public String getMessage(String path) {
        String value = ChatColor.translateAlternateColorCodes('&', (this.configuration.getString(path)));
        value = value.replace("%prefix%", ChatColor.translateAlternateColorCodes('&', (this.configuration.getString("Prefix"))));
        return value;
    }

}
