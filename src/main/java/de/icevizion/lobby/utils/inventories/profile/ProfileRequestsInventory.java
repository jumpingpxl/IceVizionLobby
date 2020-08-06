package de.icevizion.lobby.utils.inventories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.itemfactories.profile.ProfileRequestsItemFactory;
import net.titan.spigot.player.CloudPlayer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileRequestsInventory extends ProfileInventory {

	private final LobbyPlugin lobbyPlugin;
	private final ProfileRequestsItemFactory itemFactory;

	public ProfileRequestsInventory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		super(lobbyPlugin, cloudPlayer,
				lobbyPlugin.getLocales().getString(cloudPlayer, "inventoryRequestsTitle"), 49);
		this.lobbyPlugin = lobbyPlugin;
		itemFactory = new ProfileRequestsItemFactory(lobbyPlugin, cloudPlayer);
	}
}