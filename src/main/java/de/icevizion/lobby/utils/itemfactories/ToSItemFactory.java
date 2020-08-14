package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import org.bukkit.Material;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSItemFactory {

	private final Locales locales;
	private final Locale locale;

	public ToSItemFactory(LobbyPlugin lobbyPlugin, Locale locale) {
		this.locale = locale;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName(
				"§0");
	}

	public ItemBuilder getInfoItem() {
		return new ItemBuilder(Material.BOOK).setDisplayName(locales, locale, "tosInfoName").setLore(
				locales, locale, "tosInfoLore");
	}

	public ItemBuilder getAcceptItem() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 13).setDisplayName(locales,
				locale, "tosAcceptName").setLore(locales, locale, "tosAcceptLore");
	}

	public ItemBuilder getDenyItem() {
		return new ItemBuilder(Material.STAINED_CLAY).setDurability((short) 14).setDisplayName(locales,
				locale, "tosDenyName").setLore(locales, locale, "tosDenyLore");
	}
}
