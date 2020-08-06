package de.icevizion.lobby.listener.player;

import de.icevizion.lobby.LobbyPlugin;
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

		if (action == Action.RIGHT_CLICK_BLOCK
				&& event.getClickedBlock().getType() == Material.ENDER_CHEST) {
        /*
        			if (LocationUtil.compare(event.getClickedBlock().getLocation(),
					lobbyPlugin.getMapService().getLobbyMap().get().getDailyChest(), false)) {
				event.setCancelled(false);
				if (player.offlineExtradataContains("dailyReward")) {
					Inventory inventory = (Inventory) player.offlineExtradataGet("dailyReward");
					lobbyPlugin.getDailyRewardUtil().updateDyes(player, inventory);
					player.getPlayer().openInventory(inventory);
				} else {
					player.getPlayer().openInventory(lobbyPlugin.getDailyRewardUtil().buildInventory
					(player));
				}
			}
         */
			lobbyPlugin.getInventories().openDailyRewardInventory(cloudPlayer);
			return;
		}

		event.setCancelled(true);
		if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		ItemStack itemStack = event.getItem();
		if (Objects.isNull(itemStack) || !itemStack.hasItemMeta() || !itemStack.getItemMeta()
				.hasDisplayName()) {
			return;
		}

		ItemMeta itemMeta = itemStack.getItemMeta();
		String itemDisplayName = itemMeta.getDisplayName();
		String itemGames = lobbyPlugin.getLocales().getString(cloudPlayer, "itemGamesName");
		String itemProfile = lobbyPlugin.getLocales().getString(cloudPlayer, "itemProfileName");
		String itemLobbies = lobbyPlugin.getLocales().getString(cloudPlayer, "itemLobbiesName");

		if (itemDisplayName.equals(itemGames)) {
			lobbyPlugin.getInventories().openGamesInventory(cloudPlayer);
			return;
		}

		if (itemDisplayName.equals(itemProfile)) {
			lobbyPlugin.getInventories().openProfileInventory(cloudPlayer);
			return;
		}

		if (itemDisplayName.equals(itemLobbies)) {
			lobbyPlugin.getInventories().openLobbiesInventory(cloudPlayer);
			return;
		}
	}
}
