package com.tom.fraktionen.commands;

import com.tom.fraktionen.Main;
import com.tom.fraktionen.def.FactionInvite;
import com.tom.fraktionen.handlers.MessageHandler;
import com.tom.fraktionen.sql.SQLMethods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FactionCommand implements CommandExecutor , TabCompleter {


    MessageHandler mm = new MessageHandler();

    private List<FactionInvite> invites = new ArrayList<FactionInvite>();

    private static Main plugin;

    public FactionCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        // /Fraktion create [name] [type]
        if(args.length != 0) {
            if(args[0].equalsIgnoreCase("create")) {
                if(p.hasPermission("faction.create")) {
                    if(args.length == 3) {
                        if(!SQLMethods.factionExists(args[1])) {
                            int id = plugin.mySQL.getRandomFactionID();
                            if(args[2].equalsIgnoreCase("g") || args[2].equalsIgnoreCase("n") || args[2].equalsIgnoreCase("b")) {
                                String b = args[2];
                                SQLMethods.createFaction(args[1], id, b, p, 1000);
                                //p.sendMessage(plugin.getPrefix() + " &aDie Fraktion &e" + args[1] + " &awurde erstellt!");
                                p.sendMessage(mm.getMessage("Commands.MessageCreateFactionSuccess").replace("%faction%", args[1]));
                            } else {
                                //p.sendMessage(plugin.getPrefix() + " &cDer gesetzte Fraktionstyp existiert nicht!");
                                p.sendMessage(mm.getMessage("Commands.MessageCreateFailedType"));
                            }
                        } else {
                           // p.sendMessage(plugin.getPrefix() + " &cFraktion &e" + args[1] + " &cexistiert bereits!");
                            p.sendMessage(mm.getMessage("Commands.MessageCreateFailedExist").replace("%faction%", args[1]));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.DisplayCreate"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.DisplayCreate"));
                }
            } else if(args[0].equalsIgnoreCase("delete")) {
                if(p.hasPermission("faction.delete")) {
                    if(args.length == 2) {
                        if(SQLMethods.factionExists(args[1])) {
                            SQLMethods.deleteFaction(args[1]);
                            p.sendMessage(mm.getMessage("Commands.MessageDeleteFaction").replace("%faction%", args[1]));
                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageNoFactionExist"));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.DisplayDelete"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                if(p.hasPermission("faction.list")) {
                    List<String> liste = SQLMethods.getFactionList();

                    if(!liste.isEmpty()) {
                        p.sendMessage(mm.getMessage("Commands.MessageListHeader"));
                        for (String s : liste) {
                            p.sendMessage("§7- §a" + s);
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.MessageListNoFactions"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }
            } else if(args[0].equalsIgnoreCase("reset")) {

            } else if(args[0].equalsIgnoreCase("setleader")) {
                if(p.hasPermission("faction.setleader")) {
                    if(args.length == 3) {
                        if(SQLMethods.factionExists(args[1])) {
                            Player newP = Bukkit.getPlayer(args[2]);
                            if(newP != null) {
                                SQLMethods.setFactionLeader(args[1], newP);
                                p.sendMessage(mm.getMessage("Commands.MessageSetLeaderSuccess").replace("%name%", newP.getName())
                                        .replace("%faction%", args[1]));
                            } else {
                                p.sendMessage(mm.getMessage("Commands.MessageNoPlayer").replace("%name%", args[2]));
                            }
                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageNoFactionExist"));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.SetLeader"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }

            } else if(args[0].equalsIgnoreCase("info")) {
                if(p.hasPermission("faction.info")) {
                    if(args.length == 2) {
                        if(SQLMethods.factionExists(args[1])) {
                            p.sendMessage(mm.getMessage("Commands.MessageInfoHeader"));
                            p.sendMessage("§aOwner: §e" + SQLMethods.getFactionLeader(args[1]));

                            List<String> liste = SQLMethods.getFactionMember(args[1]);



                            if(!liste.isEmpty()) {
                                p.sendMessage("§aMember: ");
                                for (String s : liste) {
                                    p.sendMessage("§7- §c" + s);
                                }
                            } else {
                                p.sendMessage(mm.getMessage("Commands.MessageInfoNoMember"));
                            }

                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageNoFactionExist"));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.DisplayInfo"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }

            } else if(args[0].equalsIgnoreCase("invite")) {
                if(p.hasPermission("faction.invite")) {
                    if(args.length == 2) {
                        Player newP = Bukkit.getPlayer(args[1]);
                        if(newP != null) {
                            if(SQLMethods.getPlayerFaction(newP.getName()) == null) {
                                invites.add(new FactionInvite(p, newP));
                                p.sendMessage(mm.getMessage("Commands.MessageInviteSendFaction").replace("%name%", newP.getName()));

                                TextComponent c = new TextComponent(mm.getMessage("Commands.MessageInviteSendOwner").replace("%name%", p.getName())
                                        .replace("%faction%", SQLMethods.getPlayerFaction(p.getName())));
                                TextComponent annehmen = new TextComponent(" §a§lAnnehmen");
                                TextComponent leer = new TextComponent("§a§   ");
                                TextComponent ablehnen = new TextComponent("§c§lAblehnen");
                                annehmen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fraktion accept " + p.getName()));
                                annehmen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Annehmen!")));

                                ablehnen.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/fraktion deny " + p.getName()));
                                ablehnen.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§7Ablehnen!")));

                                c.addExtra(annehmen);
                                c.addExtra(leer);
                                c.addExtra(ablehnen);

                                newP.spigot().sendMessage(c);
                            } else {
                                p.sendMessage(mm.getMessage("Commands.MessageInviteFailed").replace("%name%", newP.getName()));
                            }
                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageNoPlayer").replace("%name%", args[1]));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.DisplayInvite"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }

            } else if(args[0].equalsIgnoreCase("accept")) {
                if(args.length == 2) {
                    String name = args[1];
                    FactionInvite invitation = null;
                    for (FactionInvite factionInvite : this.invites) {
                        if (factionInvite.getSender().getName().equals(name))
                            if (factionInvite.getReceiver().equals(p))
                                invitation = factionInvite;
                    }

                    if(invitation != null) {
                        if(SQLMethods.getPlayerFaction(name) != null) {
                            String faction = SQLMethods.getPlayerFaction(name);
                            Player inviteSender = Bukkit.getPlayer(name);

                            SQLMethods.setPlayerFaction(faction, p);
                            inviteSender.sendMessage(mm.getMessage("Commands.MessageAcceptFactionInvite").replace("%name%", p.getName()));
                            p.sendMessage(mm.getMessage("Commands.MessageAcceptFactionPlayer").replace("%faction%", faction));
                            invites.remove(invitation);
                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageAcceptFailed").replace("%name%", name));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.MessageAcceptNoInvite").replace("%name%", args[1]));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.DisplayAccept"));
                }


            } else if(args[0].equalsIgnoreCase("deny")) {
                String name = args[1];
                FactionInvite invitation = null;
                for (FactionInvite factionInvite : this.invites) {
                    if (factionInvite.getSender().getName().equals(name))
                        if (factionInvite.getReceiver().equals(p))
                            invitation = factionInvite;
                }

                if(invitation != null) {
                    Player inviteSender = Bukkit.getPlayer(name);
                    inviteSender.sendMessage(mm.getMessage("Commands.MessageDenyFactionInvite"));
                    p.sendMessage(mm.getMessage("Commands.MessageDenyFactionPlayer"));
                    invites.remove(invitation);
                } else {
                    p.sendMessage(mm.getMessage("Commands.MessageAcceptNoInvite").replace("%name%", args[1]));
                }

            } else if(args[0].equalsIgnoreCase("uninvite")) {
                if(p.hasPermission("faction.uninvite")) {
                    if(args.length == 2) {
                        Player newP = Bukkit.getPlayer(args[1]);
                        if (newP != null) {
                            if(SQLMethods.getPlayerFaction(args[1]) != null) {
                                SQLMethods.removePlayerFaction(newP);
                                newP.sendMessage(mm.getMessage("Commands.MessageKickSuccess").replace("%name%", p.getName())
                                        .replace("%clan%", SQLMethods.getPlayerFaction(p.getName())));
                                p.sendMessage(mm.getMessage("Commands.MessageKickRemoved").replace("%name%", newP.getName()));
                            } else {
                                p.sendMessage(mm.getMessage("Commands.MessageKickFailed").replace("%name%", newP.getName()));
                            }
                        } else {
                            p.sendMessage(mm.getMessage("Commands.MessageSetLeaderFailed").replace("%name%", args[1]));
                        }
                    } else {
                        p.sendMessage(mm.getMessage("Commands.DisplayKick"));
                    }
                } else {
                    p.sendMessage(mm.getMessage("Commands.NoPermission"));
                }

            } else if(args[0].equalsIgnoreCase("setrank")) {
                if(args.length == 3) {
                    boolean isNumeric = args[2].chars().allMatch( Character::isDigit );
                    if(isNumeric) {
                        int rang = Integer.parseInt(args[2]);
                        if(rang <= 6 && rang >= 0) {
                            Player myP = Bukkit.getPlayer(args[1]);
                            if(myP != null) {
                                if(SQLMethods.getPlayerRank(p.getName()) >=5) {
                                    if(SQLMethods.getPlayerFaction(p.getName()) == SQLMethods.getPlayerFaction(myP.getName())) {
                                        if(SQLMethods.getPlayerRank(p.getName()) > SQLMethods.getPlayerRank(myP.getName())) {

                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            } else if(args[0].equalsIgnoreCase("kasse")) {
                if(args[1].equalsIgnoreCase("einzahlen")) {

                } else if(args[1].equalsIgnoreCase("auszahlen")) {

                } else if(args[1].equalsIgnoreCase("info")) {

                }
            } else if(args[0].equalsIgnoreCase("help")) {
                sendHelp(p);
            }
        } else {
            sendHelp(p);
        }
        return true;
    }


    private void sendHelp(Player p) {
        /*
        p.sendMessage("                 §3§kHHHHHHHHH§r§cFraktionen§3§kHHHHHHHHH§r");
        p.sendMessage("§6/fraktion create <Name> <Typ> §c- §bUm einen Fraktion zu erstellen");
        p.sendMessage("§6/fraktion delete <Name> §c- §bUm einen Fraktion zu löschen");
        p.sendMessage("§6/fraktion list §c- §bUm alle Fraktionen anzuzeigen");
        p.sendMessage("§6/fraktion reset <Name> §c- §bUm einen Fraktion zurück zusetzten");
        p.sendMessage("§6/fraktion setLeader <Name> <Spieler> §c- §bUm den Leader einer Fraktion zu setzten");
        p.sendMessage("§6/fraktion info <Name> §c- §bUm die Member einer Fraktion zu sehen");
        p.sendMessage("§6/fraktion invite <Spieler> §c- §bUm einen Spieler in eine Fraktion hinzuzufügen");
        p.sendMessage("§6/fraktion uninvite <Name> §c- §bUm einen Spieler aus einer Fraktion zu kicken");
        p.sendMessage("§6/fraktion setRank <Spieler> <Rang> §c- §bUm den Rang eines Spielers zu setzten");
        p.sendMessage("§6/fraktion kasse einzahlen | auszahlen | info §c- §bUm die Fraktionskasse zu verwalten");
        p.sendMessage("§6/fraktion help §c- §bUm diese Hilfe anzuzeigen");
         */

        if (p.hasPermission("faction.create"))
            p.sendMessage(mm.getMessage("Commands.DisplayCreate"));
        if (p.hasPermission("faction.delete"))
            p.sendMessage(mm.getMessage("Commands.DisplayDelete"));
        if (p.hasPermission("faction.list"))
            p.sendMessage(mm.getMessage("Commands.DisplayList"));
        if (p.hasPermission("faction.reset"))
            p.sendMessage(mm.getMessage("Commands.Reset"));
        if (p.hasPermission("faction.setleader"))
            p.sendMessage(mm.getMessage("Commands.SetLeader"));
        if (p.hasPermission("faction.info"))
            p.sendMessage(mm.getMessage("Commands.DisplayInfo"));
        if (p.hasPermission("faction.setrank"))
            p.sendMessage(mm.getMessage("Commands.SetRank"));
        if (p.hasPermission("faction.kasse"))
            p.sendMessage(mm.getMessage("Commands.Kasse"));
        if (p.hasPermission("faction.invite"))
            p.sendMessage(mm.getMessage("Commands.DisplayInvite"));
        p.sendMessage(mm.getMessage("Commands.DisplayAccept"));
        if (p.hasPermission("faction.uninvite"))
            p.sendMessage(mm.getMessage("Commands.DisplayKick"));
        if (p.hasPermission("faction.reload"))
            p.sendMessage(mm.getMessage("Commands.Reload"));


    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        List<String> list = new ArrayList<>();
        Player p = (Player) sender;
        if(args.length == 1) {
            if(p.hasPermission("faction.create")) {
                list.add("create");
            }
            if(p.hasPermission("faction.delete")) {
                list.add("delete");
            }
            if(p.hasPermission("faction.invite")) {
                list.add("invite");
            }
            if(p.hasPermission("faction.uninvite")) {
                list.add("uninvite");
            }
            if(p.hasPermission("faction.info")) {
                list.add("info");
            }
            if(p.hasPermission("faction.list")) {
                list.add("list");
            }
            if(p.hasPermission("faction.reload")) {
                list.add("reload");
            }
            if(p.hasPermission("faction.setleader")) {
                list.add("setleader");
            }
            if(p.hasPermission("faction.reset")) {
                list.add("reset");
            }
            if(p.hasPermission("faction.setrank")) {
                list.add("setrank");
            }
            if(p.hasPermission("faction.kasse")) {
                list.add("kasse");
            }
            list.add("accept");
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("info")) {
                if(p.hasPermission("faction.info")) {
                    list = SQLMethods.getFactionList();
                }
            } else if(args[0].equalsIgnoreCase("invite")) {
                if(p.hasPermission("faction.invite")) {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        list.add(all.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("uninvite")) {
                if(p.hasPermission("faction.uninvite")) {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        list.add(all.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("setleader")) {
                if(p.hasPermission("faction.setleader")) {
                    for(Player all : Bukkit.getOnlinePlayers()) {
                        list.add(all.getName());
                    }
                }
            } else if(args[0].equalsIgnoreCase("reset")) {
                list = SQLMethods.getFactionList();
            } else if(args[0].equalsIgnoreCase("kasse")) {
                list.add("einzahlen");
                list.add("auszahlen");
                list.add("info");
            }
        } else if(args.length == 3) {
            if(args[0].equalsIgnoreCase("create")) {
                if(p.hasPermission("faction.create")) {
                    list.add("g");
                    list.add("n");
                    list.add("b");
                }
            } else if(args[0].equalsIgnoreCase("setrank")) {
                list.add("0");
                list.add("1");
                list.add("2");
                list.add("3");
                list.add("4");
                list.add("5");
                list.add("6");
            }
        }

        return list;
    }

}
