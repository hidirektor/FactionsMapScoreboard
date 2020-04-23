package com.infumia.t3sl4.facmap.facscoreboard.placeholder;

import org.bukkit.entity.Player;

public class PlaceholderAPI {
    public static String replace(Player player, String text) {
        return me.clip.placeholderapi.PlaceholderAPI.setPlaceholders(player, text);
    }
}
