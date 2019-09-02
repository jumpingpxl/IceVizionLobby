package de.cosmiqglow.lobby.utils;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class ParticleUtil {

    private final Set<Player> players;
    private BukkitTask task;

    public ParticleUtil() {
        this.players = new HashSet<>();
    }

    public void start(Plugin plugin, Location location) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : getPlayers()) {
                player.spawnParticle(Particle.REDSTONE, location.clone(), 5,    new Particle.DustOptions(Color.YELLOW, 0));
            }
        }, 0l, 20l);
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
