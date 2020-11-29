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

public class ProfileRequestsItemFactory extends InventoryItemFactory {

	public ProfileRequestsItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);
	}

	public InventoryItem getNoRequestsItem() {
		return createItem(new ItemBuilder(Material.BARRIER)).setDisplayName("requestsEmpty");
	}

	public InventoryItem getRequestItem(FriendProfile friendProfile, CloudPlayer requestPlayer) {
		return createItem(
				new SkullBuilder().setSkinOverValues(requestPlayer.getSkinValue())).setDisplayName(
				"requestsListName", requestPlayer.getRank().getColor(), requestPlayer.getDisplayName())
				.setLore("requestsListLore", friendProfile.getRawRequests().get(requestPlayer.getUuid()));
	}

	public InventoryItem getPreviousPageItem() {
		return createItem(new ItemBuilder(Material.ARROW)).setDisplayName("requestsPreviousPageName");
	}

	public InventoryItem getNextPageItem() {
		return createItem(new ItemBuilder(Material.ARROW)).setDisplayName("requestsNextPageName");
	}
}
