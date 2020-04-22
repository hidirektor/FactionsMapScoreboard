package com.infumia.t3sl4.facmap.utils.minecraft.player;

import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

public class FakePlayer implements OfflinePlayer {
   private final String name;
   public Team t;
   private UUID uuid = UUID.randomUUID();

   public FakePlayer(String name, Team t) {
      this.name = name;
      this.t = t;
      t.addPlayer(this);
   }

   public boolean isOnline() {
      return true;
   }

   public String getName() {
      return this.name;
   }

   public UUID getUniqueId() {
      return this.uuid;
   }

   public boolean isBanned() {
      return false;
   }

   public void setBanned(boolean banned) {
   }

   public boolean isWhitelisted() {
      return false;
   }

   public void setWhitelisted(boolean whitelisted) {
   }

   public Player getPlayer() {
      return null;
   }

   public long getFirstPlayed() {
      return 0L;
   }

   public long getLastPlayed() {
      return 0L;
   }

   public boolean hasPlayedBefore() {
      return false;
   }

   public Location getBedSpawnLocation() {
      return null;
   }

   public Map<String, Object> serialize() {
      return null;
   }

   public boolean isOp() {
      return false;
   }

   public void setOp(boolean op) {
   }

   public String toString() {
      return "FakePlayer{name='" + this.name + '\'' + "}";
   }
}
