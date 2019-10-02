package de.icevizion.lobby.listener;

import com.google.common.collect.ImmutableSet;
import de.icevizion.lobby.Lobby;
import net.titan.lib.network.spigot.ClusterSpigot;
import net.titan.spigot.Cloud;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class PlayerInteractListener implements Listener {

    private final ImmutableSet<Material> allowMaterial;
    private final Lobby plugin;

    public PlayerInteractListener(Lobby plugin) {
        this.plugin = plugin;
        this.allowMaterial = ImmutableSet.of(Material.BIRCH_BUTTON, Material.ACACIA_BUTTON,
                Material.DARK_OAK_BUTTON, Material.JUNGLE_BUTTON, Material.OAK_BUTTON,
                Material.SPRUCE_BUTTON, Material.STONE_BUTTON, Material.LEVER, Material.ACACIA_DOOR,
                Material.ACACIA_DOOR, Material.BIRCH_DOOR, Material.IRON_DOOR, Material.SPRUCE_DOOR,
                Material.SPRUCE_DOOR);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            String displayName = event.getItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§bMinispiele":
                    player.openInventory(plugin.getInventoryUtil().getTeleporter());
                    event.setCancelled(true);
                    player.updateInventory();
                    break;
                case "§eEinstellungen":
                    if (plugin.getProfileCache().getProfile(player).getSettingsInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createPanelInventory(player);
                        plugin.getProfileCache().getProfile(player).setSettingsInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getSettingsInventory());
                    }
                    break;
                case "§aSpieler Sichtbarkeit":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        plugin.getCooldownUtil().addCooldown(player, 4000L);
                        plugin.getVisibilityUtil().changeVisibility(plugin, player);
                    }
                    break;
                case "§aFreunde":
                    if (plugin.getProfileCache().getProfile(player).getFriendInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createFriendInvenotory(player);
                        plugin.getProfileCache().getProfile(player).setFriendInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getFriendInventory());
                    }
                    break;
                case "§aBauServer":
                    Optional<ClusterSpigot> clusterSpigot = Cloud.getInstance().getSpigots().stream().filter(server -> server.getServerType().equals("BuildServer")).findAny();
                    if (clusterSpigot.isPresent()) {
                        Cloud.getInstance().getPlayer(player).sendToServer(clusterSpigot.get());
                    } else {
                        player.sendMessage("§cDer Server ist nicht online");
                    }
                default:
                    break;
            }
        } else {
            if (allowMaterial.contains(event.getClickedBlock().getType())) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }

        }
    }
}
