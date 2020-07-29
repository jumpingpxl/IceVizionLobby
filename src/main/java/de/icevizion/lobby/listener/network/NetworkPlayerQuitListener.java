package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class NetworkPlayerQuitListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public NetworkPlayerQuitListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onNetworkPlayerQuit(NetworkPlayerQuitEvent event) {
		lobbyPlugin.getServer().getOnlinePlayers().forEach(player -> lobbyPlugin.getScoreboard().updateFriendsTeam(player));
	}
}
