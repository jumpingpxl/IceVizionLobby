package de.icevizion.lobby.listener.network;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class NetworkPlayerJoinListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public NetworkPlayerJoinListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onNetworkPlayerJoin(NetworkPlayerJoinEvent event) {
		lobbyPlugin.getServer().getOnlinePlayers().forEach(player -> lobbyPlugin.getScoreboard().updateFriendsTeam(player));
	}
}
