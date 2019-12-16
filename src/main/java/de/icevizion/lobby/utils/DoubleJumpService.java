package de.icevizion.lobby.utils;

import de.icevizion.aves.util.LocationUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.event.RankReloadEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 16/11/2019 16:40
 */

public class DoubleJumpService implements Listener {

    private final Set<UUID> allowedPlayers;

    private static final String DOUBLE_JUMP_PERMISSION = "lobby.doublejump";

    public DoubleJumpService() {
        allowedPlayers = new HashSet<>();
    }

    @EventHandler
    public void onRankReload(RankReloadEvent event) {
        allowedPlayers.clear();
        for (CloudPlayer cloudPlayer : Cloud.getInstance().getCurrentOnlinePlayers())
            if (cloudPlayer.hasPermission(DOUBLE_JUMP_PERMISSION))
                allowedPlayers.add(cloudPlayer.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onRankChange(PlayerRankChangeEvent event) {
        UUID uuid = event.getCloudPlayer().getPlayer().getUniqueId();
        boolean hasPerm = event.getCloudPlayer().hasPermission(DOUBLE_JUMP_PERMISSION);
        if (allowedPlayers.contains(uuid) && !hasPerm)
            allowedPlayers.remove(uuid);
        else if (!allowedPlayers.contains(uuid) && hasPerm)
            allowedPlayers.add(uuid);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (Cloud.getInstance().getPlayer(event.getPlayer()).hasPermission(DOUBLE_JUMP_PERMISSION))
            allowedPlayers.add(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        allowedPlayers.remove(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.ADVENTURE || event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            if (!player.isFlying())
                event.setCancelled(true);
            player.setAllowFlight(false);

            Vector v = player.getLocation().getDirection().multiply(1.5D).setY(1);
            player.setVelocity(v);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 10.0F, -10.0F);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if ((event.getPlayer().getGameMode() == GameMode.ADVENTURE ||
                event.getPlayer().getGameMode() == GameMode.SURVIVAL) &&
                allowedPlayers.contains(event.getPlayer().getUniqueId())) {
            if (LocationUtil.isOnGround(event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getLocation()))
                event.getPlayer().setAllowFlight(true);
        }
    }
}