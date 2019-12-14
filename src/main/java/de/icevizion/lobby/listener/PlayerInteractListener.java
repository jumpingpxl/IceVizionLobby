package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
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

        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            String displayName = event.getItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§bMinispiele":
                    if (plugin.getProfileCache().getProfile(player).getTeleporterInventory() == null) {
                        plugin.getProfileCache().getProfile(player).setTeleporterInventory(
                                plugin.getInventoryUtil().loadTeleporterInventory(player));
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getTeleporterInventory());
                    }
                    event.setCancelled(true);
                    player.updateInventory();
                    break;
                case "§aSpieler Sichtbarkeit":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                    } else {
                        plugin.getCooldownUtil().add(player, 4000L);
                        plugin.getVisibilityUtil().changeVisibility(plugin, player);
                    }
                    break;
                case "§aProfile":
                    if (plugin.getProfileCache().getProfile(player).getFriendInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createFriendInvenotory(player);
                        plugin.getProfileCache().getProfile(player).setFriendInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getFriendInventory());
                    }
                    break;
                case "§aLobby wechseln":
                    player.sendMessage("§cDieses Feature ist noch nicht aktiv");
                    //player.openInventory(plugin.getLobbyUtil().getInventory());
                    break;
                case "§5Nick":
                    player.sendMessage("§cDieses Feature ist noch nicht aktiv");
                    break;
                default:
                    break;
            }
        }
    }
}
