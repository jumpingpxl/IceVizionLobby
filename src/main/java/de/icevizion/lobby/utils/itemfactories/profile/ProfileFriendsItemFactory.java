package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.aves.item.SkullBuilder;
import de.icevizion.lobby.utils.Locales;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileFriendsItemFactory extends InventoryItemFactory {

	public ProfileFriendsItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);
	}

	public InventoryItem getNoFriendsItem() {
		return createItem(new ItemBuilder(Material.BARRIER)).setDisplayName("friendsEmpty");
	}

	public InventoryItem getOnlineFriendItem(CloudPlayer friendPlayer) {
		return createItem(
				new SkullBuilder().setSkinOverValues(friendPlayer.getSkinValue())).setDisplayName(
				"friendsOnlineName", friendPlayer.getRank().getColor(), friendPlayer.getDisplayName())
				.setLore("friendsOnlineLore", friendPlayer.getSpigot().getDisplayName());
	}

	public InventoryItem getOfflineFriendItem(CloudPlayer friendPlayer) {
		return createItem(new ItemBuilder(Material.SKULL_ITEM)).setDisplayName("friendsOfflineName",
				friendPlayer.getRank().getColor(), friendPlayer.getDisplayName()).setLore(
				"friendsOfflineLore",
				((Locales) getTranslator()).translateTimestampDifference(getCloudPlayer(),
						friendPlayer.getLastLogout()));
	}

	public InventoryItem getPreviousPageItem() {
		return createItem(new ItemBuilder(Material.ARROW)).setDisplayName("friendsPreviousPageName");
	}

	public InventoryItem getNextPageItem() {
		return createItem(new ItemBuilder(Material.ARROW)).setDisplayName("friendsNextPageName");
	}
}
