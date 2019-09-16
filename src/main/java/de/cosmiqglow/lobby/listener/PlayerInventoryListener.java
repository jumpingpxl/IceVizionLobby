package de.cosmiqglow.lobby.listener;

import de.cosmiqglow.component.partysystem.spigot.PartySystem;
import de.cosmiqglow.lobby.Lobby;
import de.cosmiqglow.lobby.profile.LobbyProfile;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
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
                if (stack.getItemMeta().getDisplayName().equals("§cSchließen")) {
                    player.closeInventory();
                } else {
                    plugin.getSettingsUtil().changeSettingsValue(event);
                }
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
            String displayName = stack.getItemMeta().getDisplayName();
            if (stack.getType().equals(Material.AIR) || displayName == " ") return;

            String name = ChatColor.stripColor(displayName.split(" ")[2]);
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

            if (cloudPlayer == null) {
                player.sendMessage("§cEs trat ein technischer Fehler auf");
                player.closeInventory();
                return;
            } else {
                switch (stack.getItemMeta().getDisplayName()) {
                    case "Nach springen":
                        break;
                    case "Party":
                        cloudPlayer.dispatchCommand("party invite", new String[]{name});
                        break;
                    case "Freund entfernen":
                        cloudPlayer.dispatchCommand("friend remove", new String[]{name});
                        //Inventory updaten
                        LobbyProfile profile = plugin.getProfileCache().getProfile(player);
                        profile.getFriendInventory().remove(event.getClickedInventory().getItem(0));
                        profile.setClickedFriend(null);
                        player.closeInventory();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}