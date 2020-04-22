package com.infumia.t3sl4.facmap.facscoreboard.listeners;

import com.infumia.t3sl4.facmap.facscoreboard.FacMap;
import com.infumia.t3sl4.facmap.facscoreboard.scoreboard.FactionScoreboard;
import com.infumia.t3sl4.facmap.utils.minecraft.scoreboard.CustomScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FactionMapListener implements Listener {
    public FactionMapListener() {
        Bukkit.getPluginManager().registerEvents(this, FacMap.inst);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (FacMap.config.getBoolean("default-state")) {
            CustomScoreboard.setScoreboard(e.getPlayer(), new FactionScoreboard(e.getPlayer()), true);
        }

        if (p.getWorld().getName().equalsIgnoreCase(FacMap.config.getString("aktif_dunya"))) {
            CustomScoreboard.updateScoreboard(e.getPlayer(), true);
            CustomScoreboard.setScoreboard(e.getPlayer(), new FactionScoreboard(e.getPlayer()), true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerChangedWorld(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        if(p.getWorld().getName().equalsIgnoreCase(FacMap.config.getString("aktif_dunya"))) {
            CustomScoreboard.updateScoreboard(e.getPlayer(), true);
            CustomScoreboard.setScoreboard(e.getPlayer(), new FactionScoreboard(e.getPlayer()), true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!e.getFrom().getChunk().equals(e.getTo().getChunk())) {
            Bukkit.getScheduler().runTask(FacMap.inst, () -> {
                CustomScoreboard sb = CustomScoreboard.getScoreboard(e.getPlayer());
                if (sb != null && sb instanceof FactionScoreboard) {
                    ((FactionScoreboard)sb).updateMap = true;
                    CustomScoreboard.updateScoreboard(e.getPlayer(), true);
                }
            });
        }
    }
}

