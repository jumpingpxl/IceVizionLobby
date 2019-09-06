package de.cosmiqglow.lobby.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class ParticleUtil {

    private final Set<Player> players;
    private BukkitTask task;

    public ParticleUtil(Plugin plugin, Location location) {
        this.players = new HashSet<>();
    }

    public void start(Plugin plugin, Location location) {
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        if (this.players.contains(player))
            this.players.add(player);
    }

    public BukkitTask getTask() {
        return task;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
