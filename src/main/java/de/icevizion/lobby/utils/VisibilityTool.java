package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;

public class VisibilityTool {

	private final LobbyPlugin lobbyPlugin;
	private final FriendSystem friendSystem;

	public VisibilityTool(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		//TODO -> Remove Singleton Pattern
		friendSystem = FriendSystem.getInstance();
	}

	public void handleJoin(CloudPlayer cloudPlayer) {
		hidePlayer(cloudPlayer.getPlayer());
		changeVisibility(cloudPlayer, cloudPlayer.getSetting(Setting.PLAYER_VISIBILITY.getId()));
	}

	public void changeVisibility(CloudPlayer cloudPlayer, int value) {
		Player player = cloudPlayer.getPlayer();
		switch (value) {
			case 0:
				player.spigot().getHiddenPlayers().forEach(player::showPlayer);
				break;
			case 1:
				FriendProfile friendProfile = friendSystem.getFriendProfile(cloudPlayer);
				lobbyPlugin.getServer().getOnlinePlayers().stream().filter(
						onlinePlayer -> onlinePlayer != player).forEach(onlinePlayer -> {
					if (friendProfile.getRawFriends().containsKey(onlinePlayer.getUniqueId().toString())) {
						player.showPlayer(onlinePlayer);
						return;
					}

					player.hidePlayer(onlinePlayer);
				});

				break;
			case 2:
				lobbyPlugin.getServer().getOnlinePlayers().stream().filter(
						onlinePlayer -> onlinePlayer != player).forEach(player::hidePlayer);
				break;
		}
	}

	private void hidePlayer(Player player) {
		lobbyPlugin.getCloud().getCurrentOnlinePlayers().forEach(cloudPlayer -> {
			switch (cloudPlayer.getSetting(Setting.PLAYER_VISIBILITY.getId())) {
				case 1:
					FriendProfile profile = friendSystem.getFriendProfile(cloudPlayer);
					if (!profile.getRawFriends().containsKey(player.getUniqueId().toString())) {
						cloudPlayer.getPlayer().hidePlayer(player);
					}

					break;
				case 2:
					cloudPlayer.getPlayer().hidePlayer(player);
					break;
			}
		});
	}
}