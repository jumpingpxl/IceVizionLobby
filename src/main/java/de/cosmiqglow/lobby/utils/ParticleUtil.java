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

    public ParticleUtil(Plugin plugin, Location location) {
        this.players = new HashSet<>();
        //start(plugin, location);
    }

    public void start(Plugin plugin, Location location) {
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            for (Player player : players) {
                for (int i = 0; i < 361; i++) {
                    if (i != 0) {
                        double angle = Math.PI * i / 180;

                        double x = 3 * Math.cos(angle);
                        double z = 3 * Math.sin(angle);

                        location.setX(location.getX() + x);
                        location.setZ(location.getZ() + z);

                        for (int i2 = 0; i2 < 361; i2++) {
                            if (i != 0) {
                                double angle2 = Math.PI * i2 / 180;

                                double x2 = 2 * Math.sin(angle2);
                                double z2 = 2 * -Math.cos(angle2);

                                location.setX(player.getLocation().getX() + x2);
                                location.setZ(player.getLocation().getZ() + z2);


                            }
                        }
                    }
                }
            }
        }, 0, 8);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        /*layer.getWorld().spawnParticle(Particle.REDSTONE, location,5, 0, 0, 0, 1,
                new Particle.DustOptions(Color.YELLOW, 0.2f));*/

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
