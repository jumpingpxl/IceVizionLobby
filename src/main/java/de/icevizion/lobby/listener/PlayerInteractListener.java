package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.Lobby;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
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
        Player player = event.getPlayer();

        if (event.getItem() == null) return;
        if (!event.getItem().hasItemMeta()) return;
        if (!event.getItem().getItemMeta().hasDisplayName()) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || (event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            String displayName = event.getItem().getItemMeta().getDisplayName();
            switch (displayName) {
                case "§e✦ §bMinispiele":
                    player.openInventory(plugin.getInventoryUtil().getTeleporter());
                    break;
                case "§e✦ Einstellungen":
                    if (plugin.getProfileCache().getProfile(player).getSettingsInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createPanelInventory(player);
                        plugin.getProfileCache().getProfile(player).setSettingsInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getSettingsInventory());
                    }
                    break;
                case "§e✦ §cBombe":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(3, plugin.getItemUtil().getPorkchop());
                        plugin.getCooldownUtil().addCooldown(player, 8000L);
                        plugin.getVisibilityUtil().changeVisibility(plugin, 2, player);
                        TNTPrimed tnt = (TNTPrimed) player.getWorld().spawnEntity(player.getLocation(),
                                EntityType.PRIMED_TNT);
                        tnt.setFuseTicks(80);
                        tnt.setVelocity(player.getLocation().getDirection().clone().normalize().
                                multiply(1.5+Math.random()));
                    }
                    break;
                case "§e✦ §dParty 'n Friends™":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(3, plugin.getItemUtil().getSlime());
                        plugin.getCooldownUtil().addCooldown(player, 4000L);
                        plugin.getVisibilityUtil().changeVisibility(plugin, 1, player);
                    }
                    break;
                case "§e✦ §aLade Spieler":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(3, plugin.getItemUtil().getTNT());
                        plugin.getCooldownUtil().addCooldown(player, 4000L);
                        plugin.getVisibilityUtil().changeVisibility(plugin, 0, player);
                    }
                    break;
                case "§e✦ §aFreunde":
                    if (plugin.getProfileCache().getProfile(player).getFriendInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createFriendInvenotory(player);
                        plugin.getProfileCache().getProfile(player).setFriendInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getFriendInventory());
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
