package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventorybuilder.ItemBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class Items {

	private final LobbyPlugin lobbyPlugin;

	public Items(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	public void setItems(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		PlayerInventory inventory = player.getInventory();

		inventory.setItem(1, getLobbiesItem(cloudPlayer).build());

		inventory.setItem(4, getGamesItem(cloudPlayer).build());
		inventory.setHeldItemSlot(4);

		inventory.setItem(1, getProfileItem(cloudPlayer).build());
	}

	public ItemBuilder getGamesItem(CloudPlayer cloudPlayer) {
		return new ItemBuilder(Material.NETHER_STAR).setDisplayName(lobbyPlugin.getLocales(),
				cloudPlayer, "itemGamesName");
	}

	public ItemBuilder getLobbiesItem(CloudPlayer cloudPlayer) {
		return new ItemBuilder(Material.WATCH).setDisplayName(lobbyPlugin.getLocales(), cloudPlayer,
				"itemLobbiesName");
	}

	public ItemBuilder getProfileItem(CloudPlayer cloudPlayer) {
		return new ItemBuilder(cloudPlayer.getSkinValue()).setDisplayName(lobbyPlugin.getLocales(),
				cloudPlayer, "itemProfileName");
	}
}
