package com.infumia.t3sl4.facmap.facscoreboard.placeholder;

import org.bukkit.entity.Player;

public class MvdwPlaceholder {
    public static String replace(Player player, String text) {
        return be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, text);
    }
}
