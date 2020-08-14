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

public class ProfileRequestManageItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;
	private final CloudPlayer requestPlayer;

	public ProfileRequestManageItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer,
	                                       CloudPlayer requestPlayer) {
		this.cloudPlayer = cloudPlayer;
		this.requestPlayer = requestPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getCancelItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setDisplayName(
				locales, cloudPlayer, "requestCancelName");
	}

	public ItemBuilder getRequesterItem(FriendProfile friendProfile) {
		return new ItemBuilder(requestPlayer.getSkinValue()).setDisplayName(locales, cloudPlayer,
				"manageRequestName", requestPlayer.getFullDisplayName()).setLore(locales, cloudPlayer,
				"manageRequestLore", friendProfile.getRawRequests().get(requestPlayer.getUuid()));
	}

	public ItemBuilder getReminderItem() {
		return new ItemBuilder(Material.BOOK).setDisplayName(locales, cloudPlayer, "requestInfoName",
				requestPlayer.getFullDisplayName());
	}

	public ItemBuilder getAcceptItem() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 13).setDisplayName(locales,
				cloudPlayer, "requestAcceptName", requestPlayer.getFullDisplayName());
	}

	public ItemBuilder getDenyServer() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14).setDisplayName(locales,
				cloudPlayer, "requestDenyName", requestPlayer.getFullDisplayName());
	}
}
