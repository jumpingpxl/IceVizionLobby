package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import net.titan.spigot.player.CloudPlayer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileRequestsItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileRequestsItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		locales = lobbyPlugin.getLocales();
		this.cloudPlayer = cloudPlayer;
	}
}
