package com.infumia.t3sl4.facmap.utils.minecraft.scoreboard;

import com.infumia.t3sl4.facmap.utils.minecraft.Minecraft;
import com.infumia.t3sl4.facmap.utils.minecraft.player.FakePlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public abstract class CustomScoreboard implements Runnable {
   private static final Map<Player, CustomScoreboard> scoreboards = new WeakHashMap();
   private List<String> old = new LinkedList();
   private List<String> values = new LinkedList();
   private final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
   private final Objective objective;
   private List<FakePlayer> players = new ArrayList();
   private char[] names = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'l', 'r', 'm', 'n', 'o', 'k'};
   private List<Character> colors = Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');
   private List<Character> modifiers = Arrays.asList('l', 'r', 'm', 'n', 'o', 'k');

   public CustomScoreboard() {
      this.scoreboard.registerNewObjective(UUID.randomUUID().toString().substring(0, 8), "dummy").setDisplaySlot(DisplaySlot.SIDEBAR);
      this.objective = this.scoreboard.getObjective(DisplaySlot.SIDEBAR);
   }

   public static CustomScoreboard getScoreboard(Player p) {
      return (CustomScoreboard)scoreboards.get(p);
   }

   public static void updateScoreboard(Player p, boolean sync) {
      CustomScoreboard sb = (CustomScoreboard)scoreboards.get(p);
      if (sb != null) {
         sb.update(sync);
      }

   }

   public static void removeScoreboard(Player p) {
      scoreboards.remove(p);
      p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
   }

   public static void setScoreboard(Player p, CustomScoreboard sb, boolean sync) {
      scoreboards.put(p, sb);
      p.setScoreboard(sb.getScoreboard());
      sb.update(sync);
   }

   public void updateValues() {
      this.old = new LinkedList(this.values);
      this.values.clear();
      this.run();
   }

   public void update(boolean sync) {
      this.updateValues();
      if (sync) {
         this.processValues();
      }

   }

   public void processValues() {
      if (!this.players.isEmpty() && this.players.size() == this.values.size()) {
         for(int i = 0; i < this.players.size(); ++i) {
            String e = (String)this.values.get(i);
            String o = (String)this.old.get(i);
            if (!e.equals(o)) {
               Team t = ((FakePlayer)this.players.get(i)).t;
               String[] text = this.splitString(e);
               t.setPrefix(text[0]);
               t.setSuffix(text[1]);
            }
         }

      } else {
         this.setupValues();
      }
   }

   private void setupValues() {
      Scoreboard var10001 = this.scoreboard;
      this.players.forEach(var10001::resetScores);
      this.players.clear();
      int pos = this.values.size();

      for(int i = 0; i < this.values.size(); ++i) {
         String e = (String)this.values.get(i);
         Team t = this.scoreboard.registerNewTeam(UUID.randomUUID().toString().substring(0, 8));
         String[] text = this.splitString(e);
         t.setPrefix(text[0]);
         t.setSuffix(text[1]);
         FakePlayer fp = new FakePlayer(Minecraft.parse("&" + this.names[i] + "&r"), t);
         this.objective.getScore(fp).setScore(pos--);
         this.players.add(fp);
      }

   }

   private String[] splitString(String string) {
      String suffixcolor = "";
      StringBuilder suffixmodifiers = new StringBuilder();
      StringBuilder prefix = new StringBuilder(string.substring(0, string.length() >= 16 ? 16 : string.length()));
      StringBuilder suffix = new StringBuilder(string.length() > 16 ? suffixcolor + suffixmodifiers + string.substring(16) : "");
      if (string.length() > 16 && string.charAt(15) == 167) {
         char c = string.charAt(16);
         if (this.colors.contains(c)) {
            suffixcolor = "§" + c;
         }

         if (this.modifiers.contains(c)) {
            suffixmodifiers.append("§").append(c);
         }

         prefix.deleteCharAt(15);
         suffix.deleteCharAt(0);
      }

      if (suffixcolor.equals("")) {
         for(int i = prefix.length() - 1; i > 0; --i) {
            char c = prefix.charAt(i);
            if (prefix.charAt(i - 1) == 167) {
               if (c == 'r') {
                  break;
               }

               if (this.colors.contains(c)) {
                  suffixcolor = "§" + c;
                  break;
               }

               if (this.modifiers.contains(c)) {
                  suffixmodifiers.append("§").append(c);
               }
            }
         }
      }

      suffix.insert(0, suffixcolor + suffixmodifiers);
      return new String[]{prefix.length() > 16 ? prefix.substring(0, 16) : prefix.toString(), suffix.length() > 16 ? suffix.substring(0, 16) : suffix.toString()};
   }

   public Objective getObjective() {
      return this.objective;
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public void addLine(String value) {
      this.values.add(Minecraft.parse(value));
   }

   public void addLine(String value, int score) {
      this.values.add(score, Minecraft.parse(value));
   }

   public void setTitle(String title) {
      String ftitle = title != null && !title.equals("") ? Minecraft.parse(title) : "§r";
      if (!this.objective.getDisplayName().equals(ftitle)) {
         this.objective.setDisplayName(ftitle);
      }

   }

   public List<String> getValues() {
      return this.values;
   }
}
