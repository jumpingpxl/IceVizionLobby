package de.cosmiqglow.lobby.map;

import org.bukkit.Location;

public class LobbyMap {

    private final Location spawn, uhc, kbffa, dailyReward;

    public LobbyMap(Location spawn, Location uhc, Location kbffa, Location dailyReward) {
        this.spawn = spawn;
        this.uhc = uhc;
        this.kbffa = kbffa;
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
