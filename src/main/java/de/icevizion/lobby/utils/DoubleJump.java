package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.event.RankReloadEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 16/11/2019 16:40
 */

public class DoubleJump implements Listener {

	private static final String DOUBLE_JUMP_PERMISSION = "lobby.doublejump";
	private final LobbyPlugin lobbyPlugin;
	private final Set<UUID> allowedPlayers;

	public DoubleJump(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		allowedPlayers = new HashSet<>();
	}

	@EventHandler
	public void onRankReload(RankReloadEvent event) {
		allowedPlayers.clear();
		lobbyPlugin.getCloud().getCurrentOnlinePlayers().stream().filter(
				cloudPlayer -> cloudPlayer.hasPermission(DOUBLE_JUMP_PERMISSION)).forEach(
				cloudPlayer -> allowedPlayers.add(cloudPlayer.getPlayer().getUniqueId()));
	}

	@EventHandler
	public void onRankChange(PlayerRankChangeEvent event) {
		UUID playerUuid = event.getCloudPlayer().getPlayer().getUniqueId();
		boolean hasPerm = event.getCloudPlayer().hasPermission(DOUBLE_JUMP_PERMISSION);

		if (allowedPlayers.contains(playerUuid) && !hasPerm) {
			allowedPlayers.remove(playerUuid);
			return;
		}

		allowedPlayers.add(playerUuid);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(event.getPlayer());
		if (cloudPlayer.hasPermission(DOUBLE_JUMP_PERMISSION)) {
			allowedPlayers.add(event.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		allowedPlayers.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);

		if (cloudPlayer.redisExtradataContains("teamSpec")) {
			return;
		}

		if (player.getGameMode() == GameMode.ADVENTURE) {
			if (!player.isFlying()) {
				event.setCancelled(true);
			}

			player.setAllowFlight(false);
			Vector vector = player.getLocation().getDirection().multiply(1.5D).setY(1);
			player.setVelocity(vector);
			player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 10.0F, -10.0F);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() != GameMode.ADVENTURE && !allowedPlayers.contains(player.getUniqueId())) {
			return;
		}

		if (!player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid()) {
			return;
		}

		player.setAllowFlight(true);
	}
}