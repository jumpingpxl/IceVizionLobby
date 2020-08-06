package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.DailyRewardInventory;
import de.icevizion.lobby.utils.inventories.GamesInventory;
import de.icevizion.lobby.utils.inventories.LobbiesInventory;
import de.icevizion.lobby.utils.inventories.ToSInventory;
import de.icevizion.lobby.utils.inventories.profile.ProfileFriendsInventory;
import net.titan.spigot.player.CloudPlayer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class Inventories {

	private final LobbyPlugin lobbyPlugin;

	public Inventories(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	public void openGamesInventory(CloudPlayer cloudPlayer) {
		if (!lobbyPlugin.getInventoryLoader().openCachedInventory(cloudPlayer, GamesInventory.class)) {
			GamesInventory gamesInventory = new GamesInventory(lobbyPlugin, cloudPlayer.getLocale());
			lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, gamesInventory);
		}
	}

	public void openLobbiesInventory(CloudPlayer cloudPlayer) {
		if (!lobbyPlugin.getInventoryLoader().openCachedInventory(cloudPlayer,
				LobbiesInventory.class)) {
			LobbiesInventory lobbiesInventory = new LobbiesInventory(lobbyPlugin,
					cloudPlayer.getLocale(),
					lobbyPlugin.getLobbySwitcher().getActiveLobbies());
			lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, lobbiesInventory);
		}
	}

	public void openToSInventory(CloudPlayer cloudPlayer) {
		if (!lobbyPlugin.getInventoryLoader().openCachedInventory(cloudPlayer, ToSInventory.class)) {
			ToSInventory toSInventory = new ToSInventory(lobbyPlugin, cloudPlayer.getLocale());
			lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, toSInventory);
		}
	}

	public void openProfileInventory(CloudPlayer cloudPlayer) {
		ProfileFriendsInventory profileInventory = new ProfileFriendsInventory(lobbyPlugin,
				cloudPlayer);
		lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, profileInventory);
	}

	public void openDailyRewardInventory(CloudPlayer cloudPlayer) {
		DailyRewardInventory dailyRewardInventory = new DailyRewardInventory(lobbyPlugin, cloudPlayer);
		lobbyPlugin.getInventoryLoader().openInventory(cloudPlayer, dailyRewardInventory);
	}
}
