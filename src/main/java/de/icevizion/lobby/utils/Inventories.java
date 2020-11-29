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
		lobbyPlugin.getInventoryService().openTranslatedInventory(cloudPlayer, GamesInventory.class,
				ifAbsent -> new GamesInventory(lobbyPlugin, cloudPlayer.getLocale()));
	}

	public void openLobbiesInventory(CloudPlayer cloudPlayer) {
		lobbyPlugin.getInventoryService().openTranslatedInventory(cloudPlayer, LobbiesInventory.class,
				ifAbsent -> new LobbiesInventory(lobbyPlugin, cloudPlayer.getLocale(),
						cloudPlayer.getSpigot(), lobbyPlugin.getLobbySwitcher().getActiveLobbies()));
	}

	public void openToSInventory(CloudPlayer cloudPlayer) {
		lobbyPlugin.getInventoryService().openTranslatedInventory(cloudPlayer, ToSInventory.class,
				ifAbsent -> new ToSInventory(lobbyPlugin, cloudPlayer.getLocale()));
	}

	public void openProfileInventory(CloudPlayer cloudPlayer) {
		lobbyPlugin.getInventoryService().openPersonalInventory(cloudPlayer,
				new ProfileFriendsInventory(lobbyPlugin, cloudPlayer));
	}

	public void openDailyRewardInventory(CloudPlayer cloudPlayer) {
		lobbyPlugin.getInventoryService().openPersonalInventory(cloudPlayer,
				new DailyRewardInventory(lobbyPlugin, cloudPlayer));
	}
}
