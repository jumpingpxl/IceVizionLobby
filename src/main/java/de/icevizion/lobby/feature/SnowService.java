package de.icevizion.lobby.feature;

import net.minecraft.server.v1_13_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_13_R2.Particles;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;

public class SnowService extends BukkitRunnable {

    private static Set<Player> players;
    private final BukkitTask runnable;

    public SnowService(Plugin plugin) {
        this.players = new HashSet<>();
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
                if (player.getWorld().getHighestBlockYAt(player.getLocation()) <= player.getLocation().getY()) {
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles
                            (Particles.w,
                                    true /* LONG DISTANCE*/,
                                    (float)player.getLocation().getX(),
                                    (float)player.getLocation().getY() + 10,
                                    (float)player.getLocation().getZ(),
                                    10 /* OFFSET X*/,
                                    10 /* OFFSET Y*/,
                                    10 /* OFFSET Z*/,
                                    0 /* data*/,
                                    15 /* count*/);
                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
                }
            }
        }
    }
}
