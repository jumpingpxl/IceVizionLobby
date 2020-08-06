package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Set;
import java.util.UUID;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerMoveListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerMoveListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Set<UUID> allowedPlayers = lobbyPlugin.getDoubleJump().getAllowedPlayers();
		if (player.getGameMode() != GameMode.ADVENTURE && !allowedPlayers.contains(
				player.getUniqueId())) {
			return;
		}

		Block block = player.getLocation().getBlock();
		Block blockRelative = block.getRelative(BlockFace.DOWN);
		if (blockRelative.getType().isSolid()) {
			return;
		}

		player.setAllowFlight(true);
	}
}
