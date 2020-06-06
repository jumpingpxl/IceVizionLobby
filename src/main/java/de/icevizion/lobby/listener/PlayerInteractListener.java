package de.icevizion.lobby.listener;

import de.icevizion.aves.util.LocationUtil;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
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
        CloudPlayer player = Cloud.getInstance().getPlayer(event.getPlayer());

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                && event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
            if (LocationUtil.compare(event.getClickedBlock().getLocation(),
                    plugin.getMapService().getLobbyMap().get().getDailyChest(), false)) {
                event.setCancelled(false);
                if (player.offlineExtradataContains("dailyReward")) {
                    Inventory inventory = (Inventory) player.offlineExtradataGet("dailyReward");
                    plugin.getDailyRewardUtil().updateDyes(player, inventory);
                    player.getPlayer().openInventory(inventory);
                } else {
                    player.getPlayer().openInventory(plugin.getDailyRewardUtil().buildInventory(player));
                }
            }
        }

        event.setCancelled(true);

        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;


        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            String displayName = event.getItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§bMinispiele":
                    player.getPlayer().openInventory(plugin.getInventoryUtil().getTeleporter());
                    break;
                case "§aProfil":
                    if (!player.offlineExtradataContains("profile")) {
                        Inventory inventory = plugin.getInventoryUtil().createFriendInventory(player);
                        player.offlineExtradataSet("profile", inventory);
                        player.getPlayer().openInventory(inventory);
                    } else {
                        player.getPlayer().openInventory((Inventory)player.offlineExtradataGet("profile"));
                    }
                    break;
                case "§aLobby wechseln":
                    if (plugin.getLobbyUtil().getCurrentSize() < 2) {
                        player.sendMessage(plugin.getPrefix() + "§cEs sind derzeit keine weiteren Lobbies online");
                    } else {
                        player.getPlayer().openInventory(plugin.getLobbyUtil().getInventory());
                    }
                    break;
            }
        }
    }
}
