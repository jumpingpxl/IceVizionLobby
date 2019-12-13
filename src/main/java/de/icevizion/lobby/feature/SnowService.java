package de.icevizion.lobby.feature;

import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SnowService extends BukkitRunnable {

    private static final int RADIUS = 10;
    private static final int AMOUNT = 25;
    private final Random random;
    private static Set<Player> players;

    private final BukkitTask runnable;

    public SnowService(Plugin plugin) {
        this.players = new HashSet<>();
        this.random = new Random();
        this.runnable = this.runTaskTimer(plugin, 0l, 2L);
    }

    public static void addPlayer(Player player) {
        players.add(player);
    }

    public static void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public void run() {
        if (players.size() != 0) {
            for (Player player : players) {
                player.spawnParticle(Particle.FIREWORKS_SPARK,
                        player.getLocation().getX(),
                        player.getLocation().getY(),
                        player.getLocation().getZ(),
                        20);
            }
        }
    }
}
