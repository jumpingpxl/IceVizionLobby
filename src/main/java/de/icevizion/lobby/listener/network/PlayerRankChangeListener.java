package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Set;
import java.util.UUID;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerRankChangeListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerRankChangeListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onPlayerRankChange(PlayerRankChangeEvent event) {
		CloudPlayer cloudPlayer = event.getCloudPlayer();
		lobbyPlugin.getLobbyScoreboard().updatePlayerNameTeam(cloudPlayer);

		Set<UUID> allowedPlayers = lobbyPlugin.getDoubleJump().getAllowedPlayers();
		UUID playerUuid = event.getCloudPlayer().getPlayer().getUniqueId();
		if (allowedPlayers.contains(playerUuid) && !cloudPlayer.hasPermission(
				lobbyPlugin.getDoubleJump().getDoubleJumpPermission())) {
			allowedPlayers.remove(playerUuid);
			return;
		}

		allowedPlayers.add(playerUuid);
	}
}
