package com.infumia.t3sl4.facmap.facscoreboard.scoreboard;

import com.infumia.t3sl4.facmap.facscoreboard.FacMap;
import com.infumia.t3sl4.facmap.facscoreboard.placeholder.PlaceholderAPI;
import com.infumia.t3sl4.facmap.facscoreboard.hook.FactionType;
import com.infumia.t3sl4.facmap.facscoreboard.placeholder.MvdwPlaceholder;
import com.infumia.t3sl4.facmap.utils.minecraft.scoreboard.CustomScoreboard;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FactionScoreboard extends CustomScoreboard {
    private Player p;
    List<String> mapCache = new ArrayList<>();
    public boolean updateMap = true;

    public FactionScoreboard(Player p) {
        this.p = p;
    }

    @Override
    public void run() {
            String title = FacMap.config.getString("scoreboard.title");
            if (FacMap.pha) title = PlaceholderAPI.replace(p, title);
            if (FacMap.mvdw) title = MvdwPlaceholder.replace(p, title);
            setTitle(title);
            for (String s : FacMap.config.getStringList("scoreboard.lines")) {
                if (s.matches("%faction-map\\|[0-9]+,[0-9]+%")) {
                    if (updateMap) {
                        mapCache.clear();
                        try {
                            String[] args = s.replace("%", "").split("\\|");
                            String[] size = args[1].split(",");
                            List<String> map = FacMap.hook.getMap(p, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                            for (String mapline : map) {
                                String mapEntry = FactionType.replaceColors(mapline);
                                addLine("   " + mapEntry);
                                mapCache.add(mapEntry);
                            }
                            updateMap = false;
                        } catch (Exception e) {
                            addLine("MapCreator failed");
                            e.printStackTrace();
                        }
                    } else {
                        for (String mapEntry : mapCache) addLine("   " + mapEntry);
                    }
                    continue;
                }
                if (FacMap.pha) s = PlaceholderAPI.replace(p, s);
                if (FacMap.mvdw) s = MvdwPlaceholder.replace(p, s);
                addLine("   " + s);
            }
    }
}
