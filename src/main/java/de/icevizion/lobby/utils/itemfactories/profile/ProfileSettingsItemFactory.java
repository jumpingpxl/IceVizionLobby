package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.Setting;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileSettingsItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileSettingsItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		locales = lobbyPlugin.getLocales();
		this.cloudPlayer = cloudPlayer;
	}

	public ItemBuilder getSecondaryBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 7).setDisplayName(
				"§0");
	}

	public ItemBuilder getCategoryItem(Setting setting) {
		return new ItemBuilder(setting.getMaterial()).setDisplayName(locales, cloudPlayer,
				setting.getKey() + "Name").setLore(locales, cloudPlayer, setting.getKey() + "Lore");
	}

	public ItemBuilder getNoneSelectedItem() {
		return new ItemBuilder(Material.BARRIER).setDisplayName(locales, cloudPlayer,
				"settingsNoneName").setLore(locales, cloudPlayer, "settingsNoneLore");
	}

	public ItemBuilder getSettingItem(Setting setting, int i) {
		return new ItemBuilder(Material.INK_SACK).setDurability((short) 8).setDisplayName(locales,
				cloudPlayer, setting.getKey() + "Setting" + (i + 1));
	}

	public ItemBuilder getSelectedSettingItem(Setting setting, int i) {
		return new ItemBuilder(Material.INK_SACK).setDurability(setting.getColors()[i]).setDisplayName(
				locales, cloudPlayer, setting.getKey() + "Setting" + (i + 1)).setLore(locales, cloudPlayer,
				"settingsSettingSelectedLore");
	}

	public void setSelectedCategoryItem(ItemBuilder itemBuilder) {
		itemBuilder.addEnchant(Enchantment.KNOCKBACK, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS);
	}
}
