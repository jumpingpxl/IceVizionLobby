package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public ToSItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		locales = lobbyPlugin.getLocales();
		this.cloudPlayer = cloudPlayer;
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName("§0");
	}

	public ItemBuilder getInfoItem() {
		return new ItemBuilder(Material.BOOK).setDisplayName(locales, cloudPlayer, "tosInfoName").setLore(locales,
				cloudPlayer, "tosInfoLore");
	}

	public ItemBuilder getAcceptItem() {
		return new ItemBuilder(Material.HARD_CLAY).setDurability((short) 13).setDisplayName(locales, cloudPlayer,
				"tosAcceptName").setLore(locales, cloudPlayer, "tosAcceptLore");
	}

	public ItemBuilder getDenyItem() {
		return new ItemBuilder(Material.HARD_CLAY).setDurability((short) 14).setDisplayName(locales, cloudPlayer,
				"tosDenyName").setLore(locales, cloudPlayer, "tosDenyLore");
	}
}
