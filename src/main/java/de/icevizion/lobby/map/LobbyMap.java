package de.icevizion.lobby.map;

import com.google.gson.annotations.JsonAdapter;
import de.cosmiqglow.aves.util.LocationUtil;
import de.icevizion.lobby.map.adapter.LocationTypeAdapter;
import org.bukkit.Location;

public class LobbyMap {

    @JsonAdapter(value = LocationTypeAdapter.class)
    private Location spawn, uhc, kbffa, dailyReward;

    public LobbyMap() {
    }

    public LobbyMap(Location spawn, Location uhc, Location kbffa, Location dailyReward) {
        this.spawn = spawn;
        this.uhc = uhc;
        this.kbffa = kbffa;
        this.dailyReward = LocationUtil.getCenter(dailyReward.getBlock());
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setUHC(Location uhc) {
        this.uhc = uhc;
    }

    public void setKBFFA(Location kbffa) {
        this.kbffa = kbffa;
    }

    public void setDailyReward(Location dailyReward) {
        this.dailyReward = dailyReward;
    }

    public Location getKBFFA() {
        return kbffa;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getUHC() {
        return uhc;
    }

    public Location getDailyReward() {
        return dailyReward;
    }
}
