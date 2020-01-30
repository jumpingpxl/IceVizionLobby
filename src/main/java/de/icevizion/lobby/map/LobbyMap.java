package de.icevizion.lobby.map;

import com.google.gson.annotations.JsonAdapter;
import de.icevizion.aves.adapter.LocationTypeAdapter;
import org.bukkit.Location;

public class LobbyMap {

    @JsonAdapter(value = LocationTypeAdapter.class)
    private Location spawn, oneline, guessit, kbffa, bedwars;
    private transient Location suicidetnt;

    public LobbyMap() { }

    public LobbyMap(Location spawn, Location oneline, Location guessit, Location kbffa, Location bedwars) {
        this.spawn = spawn;
        this.oneline = oneline;
        this.guessit = guessit;
        this.kbffa = kbffa;
        this.bedwars = bedwars;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public void setGuessit(Location guessit) {
        this.guessit = guessit;
    }

    public void setOneline(Location oneline) {
        this.oneline = oneline;
    }

    public void setKBFFA(Location kbffa) {
        this.kbffa = kbffa;
    }

    public void setBedwars(Location bedwars) {
        this.bedwars = bedwars;
    }

    public Location getKBFFA() {
        return kbffa;
    }

    public Location getSpawn() {
        return spawn;
    }

    public Location getGuessIt() {
        return guessit;
    }

    public Location getOneline() {
        return oneline;
    }

    public Location getBedwars() {
        return bedwars;
    }
}