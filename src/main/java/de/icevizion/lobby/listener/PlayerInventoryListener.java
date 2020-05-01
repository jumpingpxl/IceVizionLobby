package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import net.titan.lib.network.spigot.IClusterSpigot;
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
import org.bukkit.event.inventory.InventoryCloseEvent;
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
        Player player = (Player) event.getWhoClicked();
        if (event.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) return;
        if (event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) {
            event.setCancelled(true);
            event.setResult(Event.Result.DENY);
            player.updateInventory();
            return;
        }

        if (event.getClick().isKeyboardClick()) event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        if (!event.getCurrentItem().hasItemMeta()) return;

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        ItemStack stack = event.getCurrentItem();

        event.setCancelled(true);
        event.setResult(Event.Result.DENY);

        //Don´t remove this line of code
        player.updateInventory();

        String displayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

        switch (event.getView().getTitle()) {
            case "Einstellungen":
                plugin.getSettingsUtil().changeSettingsValue(cloudPlayer, event.getInventory(), stack, event.getSlot());
            break;
            case "Minispiele":
                player.teleport(plugin.getMapService().getLocation(displayName));
                break;
            case "Freunde":
                if (displayName.equals("Einstellungen")) {
                    if (!cloudPlayer.offlineExtradataContains("settings")) {
                        Inventory inventory = plugin.getInventoryUtil().createPanelInventory(cloudPlayer);
                        cloudPlayer.offlineExtradataSet("settings", inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory((Inventory) cloudPlayer.offlineExtradataGet("settings"));
                    }
                }

                if (displayName.equals("Freundesanfragen")) {
                    player.openInventory(plugin.getInventoryUtil().createFriendRequestInventory(cloudPlayer));
                }

                if (event.getSlot() == 47 || event.getSlot() == 51) return;
                if (stack.getType().equals(Material.SKULL_ITEM)) {
                    player.openInventory(plugin.getInventoryUtil().
                            loadActionInventory(displayName, stack));
                }
                break;
            case "Waehle eine Lobby":
                IClusterSpigot spigot = Cloud.getInstance().getSpigotByDisplayName(displayName);
                if (spigot == null) {
                    player.sendMessage(plugin.getPrefix() + "§cDieser Server ist nicht online");
                }

                if (cloudPlayer.getSpigot().getDisplayName().equals(spigot.getDisplayName())) {
                    player.sendMessage(plugin.getPrefix() + "§cDu befindest dich schon auf dem Server");
                    player.closeInventory();
                } else {
                    cloudPlayer.sendToServer(spigot);
                }
                break;
            case "Freundesanfragen":
                if (!stack.getType().equals(Material.AIR)) {
                    switch (displayName) {
                        case "Alle annehmen":
                            cloudPlayer.dispatchCommand("friend", new String[]{"acceptall"});
                            player.closeInventory();
                            break;
                        case "Alle ablehnen":
                            cloudPlayer.dispatchCommand("friend", new String[]{"denyall"});
                            player.closeInventory();
                            break;
                        default:
                            if (stack.getType().equals(Material.SKULL_ITEM)) {
                                player.openInventory(plugin.getInventoryUtil().createAcceptInventory(displayName, stack));
                            }
                            break;
                    }
                }
                break;
            case "Nutzungsbedingungen":
                switch (displayName) {
                    case "Annehmen":
                        player.sendMessage(plugin.getPrefix() + "§7Du hast die §aNutzungsbedigungen §aaktzeptiert");
                        cloudPlayer.setField("tos", System.currentTimeMillis());
                        player.closeInventory();
                        break;
                    case "Ablehnen":
                        cloudPlayer.kick("§cUm auf dem §f§oI§fce§3V§fizion.de §cNetzwerk spielen zu können,\n"+
                                "musst du unsere Nutzungsbedingungen akzeptieren!\n\n§ahttps://icevizion.de/tos-server/");
                        break;
                }
                break;
            case "Tägliche Belohnung":
                switch (displayName) {
                    case "Belohnung":
                    case "Premium Belohnung":
                        plugin.getDailyRewardUtil().giveReward(cloudPlayer, event.getInventory(), plugin.getPrefix(), displayName);
                        break;
                }
                break;
        }

        if (event.getView().getTitle().startsWith("Einstellungen für") ||
                (event.getView().getTitle().startsWith("Anfrage von"))) {
            if (stack.getType().equals(Material.AIR)) return;
                handleAction(cloudPlayer, event.getClickedInventory().getItem(9),
                    ChatColor.stripColor(event.getInventory().getItem(9).getItemMeta().getDisplayName()),
                    ChatColor.stripColor(stack.getItemMeta().getDisplayName()));
            player.closeInventory();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (Cloud.getInstance().getPlayer((Player)event.getPlayer()).getField("tos") == null &&
                event.getView().getTitle().equals("Nutzungsbedingungen")) {
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    event.getPlayer().openInventory(plugin.getInventoryUtil().getPrivacy()), 10);
        }
    }

    private void handleAction(CloudPlayer cloudPlayer, ItemStack stack, String name, String displayName) {
        switch (displayName) {
            case "Annehmen":
                cloudPlayer.dispatchCommand("friend", new String[]{"accept", name});
                break;
            case "Ablehnen":
                cloudPlayer.dispatchCommand("friend", new String[] {"deny", name});
                break;
            case "Nach springen":
                cloudPlayer.sendMessage("§cDas Feature kommt noch");
                break;
            case "Party":
                cloudPlayer.dispatchCommand("party", new String[]{"invite", name});
                break;
            case "Freund entfernen":
                cloudPlayer.dispatchCommand("friend", new String[]{"remove", name});
                ((Inventory)cloudPlayer.offlineExtradataGet("profile")).remove(stack);
                break;
            default:
                break;
        }
    }
}