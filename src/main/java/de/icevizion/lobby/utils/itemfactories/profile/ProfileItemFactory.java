package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileItemFactory extends InventoryItemFactory {

	public ProfileItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getFriendsItem() {
		return createItem(new ItemBuilder(Material.EMERALD)).setDisplayName("friendsName");
	}

	public InventoryItem getRequestsItem() {
		return createItem(new ItemBuilder(Material.PAPER)).setDisplayName("requestsName");
	}

	public InventoryItem getSettingsItem() {
		return createItem(new ItemBuilder(Material.REDSTONE_COMPARATOR)).setDisplayName(
				"settingsName");
	}

	public void setCurrentItem(ItemBuilder itemBuilder) {
		itemBuilder.addEnchantment(Enchantment.KNOCKBACK, 1)
				.addItemFlag(ItemFlag.HIDE_ENCHANTS)
				.setLore(getTranslator(), getLocale(), "currentLore");
	}
}
