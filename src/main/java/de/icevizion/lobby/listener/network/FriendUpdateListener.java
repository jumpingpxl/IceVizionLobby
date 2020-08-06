package de.icevizion.lobby.listener.network;

import de.cosmiqglow.component.friendsystem.spigot.FriendUpdateEvent;
import de.icevizion.lobby.LobbyPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class FriendUpdateListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public FriendUpdateListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@EventHandler
	public void onFriendUpdate(FriendUpdateEvent event) {
		if (event.getCloudPlayer().getPlayer() != null) {
			lobbyPlugin.getLobbyScoreboard().updateFriendsTeam(event.getCloudPlayer());
		}

		if (event.getFriendPlayer().getPlayer() != null) {
			lobbyPlugin.getLobbyScoreboard().updateFriendsTeam(event.getFriendPlayer());
		}
	}
}
