package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendsItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileFriendsItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		this.cloudPlayer = cloudPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getNoFriendsItem() {
		return new ItemBuilder(Material.BARRIER).setDisplayName(locales, cloudPlayer, "friendsEmpty");
	}

	public ItemBuilder getOnlineFriendItem(CloudPlayer friendPlayer) {
		return new ItemBuilder(friendPlayer.getSkinValue()).setDisplayName(locales, cloudPlayer,
				"friendsOnlineName", friendPlayer.getRank().getColor(), friendPlayer.getDisplayName())
				.setLore(locales, cloudPlayer, "friendsOnlineLore",
						friendPlayer.getSpigot().getDisplayName());
	}

	public ItemBuilder getOfflineFriendItem(CloudPlayer friendPlayer) {
		return new ItemBuilder(Material.SKULL_ITEM).setDisplayName(locales, cloudPlayer,
				"friendsOfflineName", friendPlayer.getRank().getColor(), friendPlayer.getDisplayName())
				.setLore(locales, cloudPlayer, "friendsOfflineLore",
						locales.translateTimestampDifference(cloudPlayer, friendPlayer.getLastLogout()));
	}

	public ItemBuilder getPreviousPageItem() {
		return new ItemBuilder(Material.ARROW).setDisplayName(locales, cloudPlayer, "friendsPreviousPageName");
	}

	public ItemBuilder getNextPageItem() {
		return new ItemBuilder(Material.ARROW).setDisplayName(locales, cloudPlayer, "friendsNextPageName");
	}
}
