package com.infumia.t3sl4.facmap.facscoreboard.api;

import org.bukkit.entity.Player;

import java.util.List;

public abstract class FactionHook {
    public abstract Relation getRelation(Object f1, Object f2) throws Exception;

    public abstract List<String> getMap(Player p, int height, int width) throws Exception;

    public enum Relation {
        WILD,
        SAFEZONE,
        WARZONE,
        ALLY,
        ENEMY,
        NEUTRAL,
        PLAYER,
        TRUCE,
        YOU
    }
}
