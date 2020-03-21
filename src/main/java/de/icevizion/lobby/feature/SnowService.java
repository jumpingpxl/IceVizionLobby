package de.icevizion.lobby.feature;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class SnowService extends BukkitRunnable {

    private static final List<Player> players = new ArrayList<>();

    private final ProtocolManager protocolManager;

    private static final WrapperPlayServerWorldParticles packet;

    static {
        packet = new WrapperPlayServerWorldParticles();
        packet.setOffsetX(10);
        packet.setOffsetY(10);
        packet.setOffsetZ(10);
      //  packet.setParticleType(WrappedParticle.create(Particle.FIREWORKS_SPARK, null));
        packet.setNumberOfParticles(35);
        packet.setLongDistance(true);
    }

    public SnowService(Plugin plugin) {
        protocolManager = ProtocolLibrary.getProtocolManager();
        runTaskTimer(plugin, 0L, 2L);
    }

    public void addPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public void run() {
        if (players.size() != 0) {
            for (Player player : players) {
                if (player.getWorld().getHighestBlockYAt(player.getEyeLocation()) <= player.getEyeLocation().getY()) {
                    packet.setX((float)player.getLocation().getX());
                    packet.setY((float)player.getLocation().getY() + 10);
                    packet.setZ((float)player.getLocation().getZ());

                    try {
                        protocolManager.sendServerPacket(player, packet.getHandle());
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}