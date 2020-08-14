package de.icevizion.lobby.utils.itemfactories.profile;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ProfileItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ProfileItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		this.cloudPlayer = cloudPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName(
				"§0");
	}

	public ItemBuilder getFriendsItem() {
		return new ItemBuilder(Material.EMERALD).setDisplayName(locales, cloudPlayer, "friendsName");
	}

	public ItemBuilder getRequestsItem() {
		return new ItemBuilder(Material.PAPER).setDisplayName(locales, cloudPlayer, "requestsName");
	}

	public ItemBuilder getSettingsItem() {
		return new ItemBuilder(Material.REDSTONE_COMPARATOR).setDisplayName(locales, cloudPlayer,
				"settingsName");
	}

	public void setCurrentItem(ItemBuilder itemBuilder) {
		itemBuilder.addEnchant(Enchantment.KNOCKBACK, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).setLore(
				locales, cloudPlayer, "currentLore");
	}
}
