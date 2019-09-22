package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.profile.LobbyProfile;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryListener implements Listener {

    private final Lobby plugin;

    public PlayerInventoryListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventory(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
        if (event.getClick().isKeyboardClick()) event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;

        Player player = (Player) event.getWhoClicked();

        ItemStack stack = event.getCurrentItem();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        //Don´t remove this line of code
        player.updateInventory();

        switch (event.getView().getTitle()) {
            case "Einstellungen":
                plugin.getSettingsUtil().changeSettingsValue(event);
            break;
            case "Minispiele":
                String locationName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
                player.teleport(plugin.getMapService().getLocation(locationName));
                break;
            case "Freunde":
                if (stack.getType().equals(Material.PLAYER_HEAD) || (stack.getType().equals(Material.SKELETON_SKULL))) {
                    plugin.getProfileCache().getProfile(player).setClickedFriend(stack.getItemMeta().getDisplayName());
                    player.openInventory(plugin.getInventoryUtil().
                            loadActionInventory(stack.getItemMeta().getDisplayName(), stack));
                }
                break;
        }

        if (event.getView().getTitle().equals(plugin.getProfileCache().getProfile(player).getClickedFriend())) {
            if (stack.getType().equals(Material.AIR)) return;

            String name = ChatColor.stripColor(event.getClickedInventory().getItem(9).getItemMeta().getDisplayName());
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

            if (cloudPlayer == null) {
                player.sendMessage("§cEs trat ein technischer Fehler auf");
                player.closeInventory();
                return;
            } else {
                switch (stack.getItemMeta().getDisplayName()) {
                    case "Nach springen":
                        player.sendMessage("§cDas Feature kommt noch");
                        break;
                    case "Party":
                        cloudPlayer.dispatchCommand("party", new String[]{"invite", name});
                        break;
                    case "Freund entfernen":
                        cloudPlayer.dispatchCommand("friend", new String[]{"remove", name});
                        //Inventory updaten
                        LobbyProfile profile = plugin.getProfileCache().getProfile(player);
                        profile.getFriendInventory().remove(event.getClickedInventory().getItem(9));
                        profile.setClickedFriend(null);
                        break;
                    default:
                        break;
                }
                player.closeInventory();
            }
        }
    }

    @EventHandler
    public void onMove(InventoryMoveItemEvent event) {
        event.setCancelled(true);
    }
}