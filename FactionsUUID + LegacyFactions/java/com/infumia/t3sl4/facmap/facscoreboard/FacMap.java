package com.infumia.t3sl4.facmap.facscoreboard;

import com.infumia.t3sl4.facmap.facscoreboard.api.FactionHook;
import com.infumia.t3sl4.facmap.facscoreboard.hook.FactionHookManager;
import com.infumia.t3sl4.facmap.facscoreboard.hook.FactionType;
import com.infumia.t3sl4.facmap.facscoreboard.listeners.FactionMapListener;
import com.infumia.t3sl4.facmap.facscoreboard.scoreboard.FactionScoreboard;
import com.infumia.t3sl4.facmap.utils.console.logging.basic.Log;
import com.infumia.t3sl4.facmap.utils.minecraft.scoreboard.CustomScoreboard;
import com.infumia.t3sl4.facmap.utils.yaml.config.Config;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FacMap extends JavaPlugin {
    public static FacMap inst;
    public static Config config;
    public static FactionHook hook;
    public static boolean pha = false, mvdw = false;

    public FacMap() {
        FacMap.inst = this;
    }

    public static String chatcolor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    @Override
    public void onEnable() {
        config = new Config("config.yml", getResource("config.yml"), this);
        if (config.getInt("version") != 2) {
            config.file.renameTo(new File(config.file.getParentFile(), "config-old.yml"));
            config = new Config("config.yml", getResource("config.yml"), this);
        }
        new FactionMapListener();
        FactionType.values();
        hook = FactionHookManager.getHook();
        if (hook == null) {
            Log.severe("No compatible hook was found, plugin will now disable");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        int update_interval = config.getInt("scoreboard.refresh-rate");
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if(player.getWorld().getName().equalsIgnoreCase(config.getString("aktif_edilecek_dunya"))) {
                    CustomScoreboard.updateScoreboard(player, true);
                } else {
                    CustomScoreboard.removeScoreboard(player);
                }
            }
        }, update_interval, update_interval);
        pha = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
        if (!pha) Log.warn("PlaceholderAPI Sunucunuzda Kurulu Degil.");
        mvdw = Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI");
        if (!mvdw) Log.warn("MVdWPlaceholderAPI Sunucunuzda Kurulu Degil");
        Bukkit.getConsoleSender().sendMessage("   ");
        Bukkit.getConsoleSender().sendMessage("  ___            __                       _         ");
        Bukkit.getConsoleSender().sendMessage(" |_ _|  _ __    / _|  _   _   _ __ ___   (_)   __ _ ");
        Bukkit.getConsoleSender().sendMessage("  | |  | '_ \\  | |_  | | | | | '_ ` _ \\  | |  / _` |");
        Bukkit.getConsoleSender().sendMessage("  | |  | | | | |  _| | |_| | | | | | | | | | | (_| |");
        Bukkit.getConsoleSender().sendMessage(" |___| |_| |_| |_|    \\__,_| |_| |_| |_| |_|  \\__,_|");
        Bukkit.getConsoleSender().sendMessage("    ");
    }

    @Override
    public void onDisable() {
        FacMap.inst = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player phover = (Player) sender;
        TextComponent msg = new TextComponent("§e§lAuthor §7|| §e§lYapımcı");
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Eklenti Yapımcısı:\n   §8§l» §eSYN_T3SL4 \n   §8§l» §7Discord: §eHalil#4439").create()));
        if (args.length == 0) {
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-1")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-2")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-3")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-4")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-5")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-6")));
            sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-7")));
            phover.spigot().sendMessage(msg);
        } else {
            switch (args[0].toLowerCase()) {
                case "info":
                case "explain": {
                    Player p = (Player) sender;
                    if (sender.hasPermission("infmap.info")) {
                        sender.sendMessage(chatcolor(config.getString("messages.map.anasatir")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.sen")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.guvenlialan")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.savasalani")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.bosarazi")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.klanarazisi")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.dostarazi")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.dusmanarazi")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.tarafsizarazi")));
                        sender.sendMessage(chatcolor(config.getString("messages.map.ateskesarazi")));
                        //for (FactionType type : FactionType.values()) {
                            //sender.sendMessage(String.format("\u00a7%s\u2588 \u00a7f %s", type.color, type.displayname));
                        //}
                    }
                    break;
                }
                case "toggle":
                case "map": {
                    Player p = (Player) sender;
                    if (sender.hasPermission("infmap.toggle")) {
                        if (CustomScoreboard.getScoreboard(p) == null) {
                            CustomScoreboard.setScoreboard(p, new FactionScoreboard(p), true);
                            sender.sendMessage(chatcolor(config.getString("messages.scoreboard.aktifedildi")));
                        } else {
                            CustomScoreboard.removeScoreboard(p);
                            sender.sendMessage(chatcolor(config.getString("messages.scoreboard.kapatildi")));
                        }
                    }
                    break;
                }
                case "reload": {
                    if (sender.hasPermission("infmap.reload")) {
                        config.reload();
                        FactionType.reload();
                        sender.sendMessage(chatcolor(config.getString("messages.reload")));
                    }
                    break;
                }
                default:
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-1")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-2")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-3")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-4")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-5")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-6")));
                    sender.sendMessage(chatcolor(config.getString("komutlar.komut-bilgi-7")));
                    phover.spigot().sendMessage(msg);
            }
        }
        return true;
    }
}
