package de.icevizion.lobby.utils.itemfactories;

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

public class LobbiesItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public LobbiesItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		locales = lobbyPlugin.getLocales();
		this.cloudPlayer = cloudPlayer;
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName("§0");
	}

	public ItemBuilder getLobbyItem(String serverName, Integer playerCount) {
		return new ItemBuilder(Material.GLOWSTONE_DUST).setDisplayName(locales, cloudPlayer, "lobbyName", serverName)
				.setLore(locales, cloudPlayer, "lobbyLore", playerCount);
	}

	public ItemBuilder getCurrentLobbyItem(String serverName, Integer playerCount) {
		return new ItemBuilder(Material.GLOWSTONE_DUST).addEnchant(Enchantment.KNOCKBACK, 1).addItemFlags(
				ItemFlag.HIDE_ENCHANTS).setDisplayName(locales, cloudPlayer, "currentLobbyName", serverName).setLore(locales,
				cloudPlayer, "currentLobbyLore", playerCount);
	}
}
