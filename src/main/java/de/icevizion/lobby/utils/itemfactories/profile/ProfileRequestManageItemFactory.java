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

public class ProfileRequestManageItemFactory extends InventoryItemFactory {

	private final CloudPlayer requestPlayer;

	public ProfileRequestManageItemFactory(Translator translator, CloudPlayer cloudPlayer,
	                                       CloudPlayer requestPlayer) {
		super(translator, cloudPlayer);

		this.requestPlayer = requestPlayer;
	}

	public InventoryItem getCancelItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14)).setDisplayName(
				"requestCancelName");
	}

	public InventoryItem getRequesterItem(FriendProfile friendProfile) {
		return createItem(
				new SkullBuilder().setSkinOverValues(requestPlayer.getSkinValue())).setDisplayName(
				"manageRequestName", requestPlayer.getFullDisplayName()).setLore("manageRequestLore",
				friendProfile.getRawRequests().get(requestPlayer.getUuid()));
	}

	public InventoryItem getReminderItem() {
		return createItem(new ItemBuilder(Material.BOOK)).setDisplayName("requestInfoName",
				requestPlayer.getFullDisplayName());
	}

	public InventoryItem getAcceptItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 13)).setDisplayName(
				"requestAcceptName", requestPlayer.getFullDisplayName());
	}

	public InventoryItem getDenyItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14)).setDisplayName(
				"requestDenyName", requestPlayer.getFullDisplayName());
	}
}
