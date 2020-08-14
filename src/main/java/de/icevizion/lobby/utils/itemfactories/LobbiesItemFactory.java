package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LobbiesItemFactory {

	private final Locales locales;
	private final Locale locale;

	public LobbiesItemFactory(LobbyPlugin lobbyPlugin, Locale locale) {
		locales = lobbyPlugin.getLocales();
		this.locale = locale;
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName(
				"§0");
	}

	public ItemBuilder getLobbyItem(String serverName, Integer playerCount) {
		return new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName(locales, locale, "lobbyName",
				serverName).setLore(locales, locale, "lobbyLore", playerCount);
	}

	public ItemBuilder getCurrentLobbyItem(String serverName, Integer playerCount) {
		return new ItemBuilder(Material.GLOWSTONE_DUST).addEnchant(Enchantment.KNOCKBACK, 1)
				.addItemFlags(ItemFlag.HIDE_ENCHANTS)
				.setDisplayName(locales, locale, "currentLobbyName", serverName)
				.setLore(locales, locale, "currentLobbyLore", playerCount);
	}
}
