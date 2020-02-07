package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import net.sf.cglib.asm.$Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

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

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getHand().equals(EquipmentSlot.HAND)) {
            if (event.getClickedBlock().getType().equals(Material.OAK_STAIRS)
                    && event.getClickedBlock().getLocation().subtract(0, -1, 0).
                    getBlock().getType().equals(Material.PURPUR_BLOCK)) {
                Location arrow = event.getClickedBlock().getLocation().add(0.5, 0, 0.5);
                player.getWorld().spawnArrow(arrow, new Vector(), 0.0f, 0.0f).addPassenger(player);
            }
        }

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
                    player.openInventory(plugin.getLobbyUtil().getInventory());
                    break;
                case "§5Nick":
                    player.sendMessage(plugin.getPrefix() + "§cDieses Feature ist noch nicht aktiv");
                    break;
            }
        }
    }
}
