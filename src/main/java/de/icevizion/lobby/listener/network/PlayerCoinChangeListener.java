package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.PlayerCoinChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class PlayerCoinChangeListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerCoinChangeListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onPlayerCoinChange(PlayerCoinChangeEvent event) {
		lobbyPlugin.getLobbyScoreboard().updateCoinsTeam(event.getCloudPlayer());
	}
}
