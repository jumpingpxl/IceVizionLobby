package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

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
                    player.openInventory(plugin.getInventoryUtil().getPanel(player));
                    break;
                case "§e✦ §cBombe":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(4, plugin.getItemUtil().getPorkchop());
                        plugin.getCooldownUtil().addCooldown(player, System.currentTimeMillis() + 3000L);
                    }
                    player.sendMessage("§cBald");
                    break;
                case "§e✦ §dSpielerschweinchen":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(4, plugin.getItemUtil().getSlime());
                        plugin.getCooldownUtil().addCooldown(player, System.currentTimeMillis() + 3000L);
                    }
                    player.sendMessage("Tests");
                    break;
                case "§e✦ §aLade Spieler":
                    if (plugin.getCooldownUtil().hasCooldown(player)) {
                        player.sendMessage("§cBitte warte noch kurz");
                        return;
                    } else {
                        player.getInventory().remove(event.getItem());
                        player.getInventory().setItem(4, plugin.getItemUtil().getTNT());
                        plugin.getCooldownUtil().addCooldown(player, System.currentTimeMillis() + 3000L);
                    }
                    player.sendMessage("Testss");
                    break;
                default:
                    break;
            }
        }
    }
}
