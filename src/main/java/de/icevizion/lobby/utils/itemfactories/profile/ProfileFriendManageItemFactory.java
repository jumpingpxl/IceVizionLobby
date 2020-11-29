package de.icevizion.lobby.utils.itemfactories.profile;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.aves.item.SkullBuilder;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendManageItemFactory extends InventoryItemFactory {

	public ProfileFriendManageItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);
	}

	public InventoryItem getCancelItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14)).setDisplayName(
				"manageCancelName");
	}

	public InventoryItem getFriendItem(FriendProfile friendProfile, CloudPlayer friendPlayer) {
		boolean isOnline = friendPlayer.isOnline();
		return createItem(
				new SkullBuilder().setSkinOverValues(friendPlayer.getSkinValue())).setDisplayName(
				"manageFriendName", friendPlayer.getFullDisplayName()).setLore("manageFriendLore",
				isOnline ? 1 : 0, friendProfile.getRawFriends().get(friendPlayer.getUuid()),
				isOnline ? friendPlayer.getSpigot().getDisplayName() : "", friendPlayer.getLastLogout());
	}

	public InventoryItem getJumpItem() {
		return createItem(new ItemBuilder(Material.ENDER_PEARL)).setDisplayName("manageJumpName");
	}

	public InventoryItem getInviteItem() {
		return createItem(new ItemBuilder(Material.CAKE)).setDisplayName("manageInviteName");
	}

	public InventoryItem getRemoveItem() {
		return createItem(new ItemBuilder(Material.BARRIER)).setDisplayName("manageRemoveName");
	}
}
