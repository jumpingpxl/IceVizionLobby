package de.icevizion.lobby.listener;

import de.icevizion.aves.util.LocationUtil;
import de.icevizion.lobby.Lobby;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    private final Lobby plugin;

    public PlayerInteractListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().getType().equals(Material.ENDER_CHEST))) {
            if (LocationUtil.compare(event.getClickedBlock().getLocation(), plugin.getMapService().getLobbyMap().get().getDailyChest(), false)) {
                event.setCancelled(true);
                plugin.getDailyRewardUtil().checkDailyReward(plugin.getPrefix(), player);
            }
        }

        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;


        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            String displayName = event.getItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§bMinispiele":
                    player.openInventory(plugin.getInventoryUtil().getTeleporter());
                    break;
                case "§aProfil":
                    if (plugin.getProfileCache().getProfile(player).getFriendInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createFriendInventory(player);
                        plugin.getProfileCache().getProfile(player).setFriendInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getFriendInventory());
                    }
                    break;
                case "§aLobby wechseln":
                    if (plugin.getLobbyUtil().getCurrentSize() < 2) {
                        player.sendMessage(plugin.getPrefix() + "§cEs sind derzeit keine weiteren Lobbies online");
                    } else {
                        player.openInventory(plugin.getLobbyUtil().getInventory());
                    }
                    break;
            }
        }
    }
}
