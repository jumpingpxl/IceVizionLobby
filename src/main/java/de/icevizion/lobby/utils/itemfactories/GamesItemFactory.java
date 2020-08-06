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

public class GamesItemFactory {

	private final Locales locales;
	private final Locale locale;

	public GamesItemFactory(LobbyPlugin lobbyPlugin, Locale locale) {
		locales = lobbyPlugin.getLocales();
		this.locale = locale;
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName(
				"§0");
	}

	public ItemBuilder getSpawnItem() {
		return new ItemBuilder(Material.NETHER_STAR).setDisplayName(locales, locale, "spawnName");
	}

	public ItemBuilder getGuessItItem() {
		return new ItemBuilder(Material.BOOK_AND_QUILL).setDisplayName(locales, locale, "guessItName");
	}

	public ItemBuilder getKnockBackFFAItem() {
		return new ItemBuilder(Material.STICK).addEnchant(Enchantment.KNOCKBACK, 1).addItemFlags(
				ItemFlag.HIDE_ENCHANTS).setDisplayName(locales, locale, "kbffaName");
	}

	public ItemBuilder getOneLineItem() {
		return new ItemBuilder(Material.SANDSTONE).setDisplayName(locales, locale, "oneLineName");
	}

	public ItemBuilder getBedWarsItem() {
		return new ItemBuilder(Material.BED).setDisplayName(locales, locale, "bedWarsName");
	}
}
