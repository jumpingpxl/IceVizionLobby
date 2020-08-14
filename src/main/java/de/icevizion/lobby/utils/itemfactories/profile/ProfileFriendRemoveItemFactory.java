package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendRemoveItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;
	private final CloudPlayer friendPlayer;

	public ProfileFriendRemoveItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                                      CloudPlayer friendPlayer) {
		this.cloudPlayer = cloudPlayer;
		this.friendPlayer = friendPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getReminderItem() {
		return new ItemBuilder(Material.BOOK).setDisplayName(locales, cloudPlayer, "removeInfoName",
				friendPlayer.getFullDisplayName());
	}

	public ItemBuilder getConfirmItem() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 13).setDisplayName(locales,
				cloudPlayer, "removeConfirmName", friendPlayer.getFullDisplayName());
	}

	public ItemBuilder getAbortItem() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14).setDisplayName(locales,
				cloudPlayer, "removeAbortName", friendPlayer.getFullDisplayName());
	}
}
