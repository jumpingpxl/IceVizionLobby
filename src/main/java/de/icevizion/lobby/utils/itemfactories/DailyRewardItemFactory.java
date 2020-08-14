package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.Locales;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class DailyRewardItemFactory {

	private final Locales locales;
	private final CloudPlayer cloudPlayer;

	public DailyRewardItemFactory(LobbyPlugin lobbyPlugin, CloudPlayer cloudPlayer) {
		this.cloudPlayer = cloudPlayer;

		locales = lobbyPlugin.getLocales();
	}

	public ItemBuilder getBackgroundItem() {
		return new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 3).setDisplayName(
				"§0");
	}

	public ItemBuilder getRewardItem() {
		return new ItemBuilder(Material.INK_SACK).setDurability((short) 10).setDisplayName(locales,
				cloudPlayer, "dailyName");
	}

	public ItemBuilder getClaimedRewardItem() {
		return new ItemBuilder(Material.INK_SACK).setDurability((short) 8).setDisplayName(locales,
				cloudPlayer, "dailyClaimedName").setLore(locales, cloudPlayer, "dailyClaimedLore");
	}

	public ItemBuilder getPremiumRewardItem() {
		return new ItemBuilder(Material.INK_SACK).setDurability((short) 10).setDisplayName(locales,
				cloudPlayer, "dailyPremiumName");
	}

	public ItemBuilder getClaimedPremiumRewardItem() {
		return new ItemBuilder(Material.INK_SACK).setDurability((short) 8).setDisplayName(locales,
				cloudPlayer, "dailyClaimedPremiumName").setLore(locales, cloudPlayer,
				"dailyClaimedPremiumLore");
	}
}
