package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.utils.Setting;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileSettingsItemFactory extends InventoryItemFactory {

	public ProfileSettingsItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getSecondaryBackgroundItem() {
		return createItem(new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.GRAY))
				.setEmptyDisplayName();
	}

	public InventoryItem getCategoryItem(Setting setting) {
		return createItem(new ItemBuilder(setting.getMaterial())).setDisplayName(
				setting.getKey() + "Name").setLore(setting.getKey() + "Lore");
	}

	public InventoryItem getNoneSelectedItem() {
		return createItem(new ItemBuilder(Material.BARRIER)).setDisplayName("settingsNoneName").setLore(
				"settingsNoneLore");
	}

	public InventoryItem getSettingItem(Setting setting, int i) {
		return createItem(new ItemBuilder(Material.INK_SACK).setDurability((short) 8)).setDisplayName(
				setting.getKey() + "Setting" + (i + 1));
	}

	public InventoryItem getSelectedSettingItem(Setting setting, int i) {
		return createItem(
				new ItemBuilder(Material.INK_SACK).setDurability(setting.getColors()[i])).setDisplayName(
				setting.getKey() + "Setting" + (i + 1)).setLore("settingsSettingSelectedLore");
	}

	public void setSelectedCategoryItem(ItemBuilder itemBuilder) {
		itemBuilder.addEnchantment(Enchantment.KNOCKBACK, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS);
	}
}
