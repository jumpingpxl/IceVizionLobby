package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.lobby.utils.inventories.GamesInventory;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class PlayerInteractListener implements Listener {

	private final LobbyPlugin lobbyPlugin;

	public PlayerInteractListener(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	//TODO -> finish

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(event.getPlayer());
		Action action = event.getAction();

		if (action == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.ENDER_CHEST) {
        /*
        			if (LocationUtil.compare(event.getClickedBlock().getLocation(),
					lobbyPlugin.getMapService().getLobbyMap().get().getDailyChest(), false)) {
				event.setCancelled(false);
				if (player.offlineExtradataContains("dailyReward")) {
					Inventory inventory = (Inventory) player.offlineExtradataGet("dailyReward");
					lobbyPlugin.getDailyRewardUtil().updateDyes(player, inventory);
					player.getPlayer().openInventory(inventory);
				} else {
					player.getPlayer().openInventory(lobbyPlugin.getDailyRewardUtil().buildInventory(player));
				}
			}
         */
			return;
		}

		event.setCancelled(true);
		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		ItemStack itemStack = event.getItem();
		if (Objects.isNull(itemStack) || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) {
			return;
		}

		ItemMeta itemMeta = itemStack.getItemMeta();
		String itemDisplayName = itemMeta.getDisplayName();
		String itemGames = lobbyPlugin.getLocales().getString(cloudPlayer, "itemGamesName");
		String itemProfile = lobbyPlugin.getLocales().getString(cloudPlayer, "itemProfileName");
		String itemLobbies = lobbyPlugin.getLocales().getString(cloudPlayer, "itemLobbiesName");

		if (itemDisplayName.equals(itemGames)) {
			GamesInventory gamesInventory = new GamesInventory(lobbyPlugin, cloudPlayer);
			lobbyPlugin.getInventoryLoader().openInventory(gamesInventory);
			return;
		}

		if (itemDisplayName.equals(itemProfile)) {
			/*
			if (!player.offlineExtradataContains("profile")) {
						Inventory inventory = lobbyPlugin.getInventoryUtil().createFriendInventory(player);
						player.offlineExtradataSet("profile", inventory);
						player.getPlayer().openInventory(inventory);
					} else {
						player.getPlayer().openInventory((Inventory) player.offlineExtradataGet("profile"));
					}
					*/
			return;
		}

		if (itemDisplayName.equals(itemLobbies)) {
			/*
					if (lobbyPlugin.getLobbyUtil().getCurrentSize() < 2) {
						player.sendMessage(lobbyPlugin.getPrefix() + "§cEs sind derzeit keine weiteren Lobbies online");
					} else {
						player.getPlayer().openInventory(lobbyPlugin.getLobbyUtil().getInventory());
					}
			 */
			return;
		}
	}
}
