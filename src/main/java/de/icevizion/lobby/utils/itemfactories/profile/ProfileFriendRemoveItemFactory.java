package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendRemoveItemFactory extends InventoryItemFactory {

	private final CloudPlayer friendPlayer;

	public ProfileFriendRemoveItemFactory(Translator translator, CloudPlayer cloudPlayer,
	                                      CloudPlayer friendPlayer) {
		super(translator, cloudPlayer);

		this.friendPlayer = friendPlayer;
	}

	public InventoryItem getReminderItem() {
		return createItem(new ItemBuilder(Material.BOOK)).setDisplayName("removeInfoName",
				friendPlayer.getFullDisplayName());
	}

	public InventoryItem getConfirmItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 13)).setDisplayName(
				"removeConfirmName", friendPlayer.getFullDisplayName());
	}

	public InventoryItem getAbortItem() {
		return createItem(
				new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14)).setDisplayName(
				"removeAbortName", friendPlayer.getFullDisplayName());
	}
}
