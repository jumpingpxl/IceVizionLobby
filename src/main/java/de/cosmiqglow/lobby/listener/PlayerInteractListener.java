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
                    player.sendMessage("§cBald");
                    break;
                case "§e✦ §dSpielerschweinchen":
                    player.sendMessage("Tests");
                    break;
                case "§e✦ §aLade Spieler":
                    player.sendMessage("Testss");
                    break;
                default:
                    break;
            }
        }
    }
}
