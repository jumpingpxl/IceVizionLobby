package de.icevizion.lobby.utils.itemfactories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileRequestsItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileRequestsItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		this.cloudPlayer = cloudPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getNoRequestsItem() {
		return new ItemBuilder(Material.BARRIER).setDisplayName(locales, cloudPlayer, "requestsEmpty");
	}

	public ItemBuilder getRequestItem(FriendProfile friendProfile, CloudPlayer requestPlayer) {
		return new ItemBuilder(requestPlayer.getSkinValue()).setDisplayName(locales, cloudPlayer,
				"requestsListName", requestPlayer.getRank().getColor(), requestPlayer.getDisplayName())
				.setLore(locales, cloudPlayer, "requestsListLore",
						friendProfile.getRawRequests().get(requestPlayer.getUuid()));
	}

	public ItemBuilder getPreviousPageItem() {
		return new ItemBuilder(Material.ARROW).setDisplayName(locales, cloudPlayer,
				"requestsPreviousPageName");
	}

	public ItemBuilder getNextPageItem() {
		return new ItemBuilder(Material.ARROW).setDisplayName(locales, cloudPlayer,
				"requestsNextPageName");
	}
}
