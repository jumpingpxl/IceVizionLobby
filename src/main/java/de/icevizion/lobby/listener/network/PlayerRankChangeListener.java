package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.PlayerRankChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
		lobbyPlugin.getScoreboard().updatePlayerNameTeam(event.getCloudPlayer());
	}
}
