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

public class ProfileFriendManageItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileFriendManageItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		this.cloudPlayer = cloudPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getCancelItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setDisplayName(
				locales, cloudPlayer, "manageCancelName");
	}

	public ItemBuilder getFriendItem(FriendProfile friendProfile, CloudPlayer friendPlayer) {
		boolean isOnline = friendPlayer.isOnline();
		return new ItemBuilder(friendPlayer.getSkinValue()).setDisplayName(locales, cloudPlayer,
				"manageFriendName", friendPlayer.getFullDisplayName()).setLore(locales, cloudPlayer,
				"manageFriendLore", isOnline ? 1 : 0,
				friendProfile.getRawFriends().get(friendPlayer.getUuid()),
				isOnline ? friendPlayer.getSpigot().getDisplayName() : "", friendPlayer.getLastLogout());
	}

	public ItemBuilder getJumpItem() {
		return new ItemBuilder(Material.ENDER_PEARL).setDisplayName(locales, cloudPlayer,
				"manageJumpName");
	}

	public ItemBuilder getInviteItem() {
		return new ItemBuilder(Material.CAKE).setDisplayName(locales, cloudPlayer, "manageInviteName");
	}

	public ItemBuilder getRemoveItem() {
		return new ItemBuilder(Material.BARRIER).setDisplayName(locales, cloudPlayer,
				"manageRemoveName");
	}
}
