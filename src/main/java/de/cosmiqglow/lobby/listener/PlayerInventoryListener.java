package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.lobby.Lobby;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryListener implements Listener {

    private final Lobby plugin;

    public PlayerInventoryListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        if (event.getClickedInventory() == null) return;
        if (event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;
        Player player = (Player) event.getWhoClicked();

        ItemStack stack = event.getCurrentItem();

        switch (event.getView().getTitle()) {
            case "Einstellungen":
                if (stack.getItemMeta().getDisplayName().equals("§cSchließen")) {
                    player.closeInventory();
                } else {
                    //TODO: Einstellungen machen
                }
                player.sendMessage("Settings");
                break;
            case "Minispiele":
                player.sendMessage("Teleporter");
                break;
        }
    }
}