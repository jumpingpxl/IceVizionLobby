package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileFriendsItemFactory;
import net.titan.spigot.player.CloudPlayer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendsInventory extends ProfileInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileFriendsItemFactory itemFactory;

	public ProfileFriendsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer,
				lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryFriendsTitle"), 47);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new ProfileFriendsItemFactory(lobbyPlugin, cloudPlayer);
	}
}
