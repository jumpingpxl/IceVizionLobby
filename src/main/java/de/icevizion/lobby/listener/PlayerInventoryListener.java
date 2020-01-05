package de.icevizion.lobby.listener;

import de.icevizion.aves.util.CooldownUtil;
import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.profile.LobbyProfile;
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

import java.util.regex.Pattern;

public class PlayerInventoryListener implements Listener {

    private final Pattern friendPattern;
    private final Lobby plugin;

    private final CooldownUtil cooldownUtil;

    public PlayerInventoryListener(Lobby plugin) {
        this.plugin = plugin;
        this.friendPattern = Pattern.compile("(Anfrage von [a-zA-Z]{4,16})|(Einstellung für [a-zA-Z]{4,16})");
        this.cooldownUtil = new CooldownUtil();
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
                LobbyProfile profile = plugin.getProfileCache().getProfile(player);
                if (cooldownUtil.hasCooldown(player)) {
                    Bukkit.broadcastMessage("cooldown");
                    event.setCancelled(true);
                    player.updateInventory();
                } else {
                    plugin.getSettingsUtil().changeSettingsValue(cloudPlayer, profile,
                            event.getInventory(), stack, event.getSlot());
                    cooldownUtil.add(player, 1000);
                }

            break;
            case "Minispiele":
                player.teleport(plugin.getMapService().getLocation(displayName));
                break;
            case "Freunde":
                if (stack.getItemMeta().getDisplayName().equals("§cEinstellungen")) {
                    if (plugin.getProfileCache().getProfile(player).getSettingsInventory() == null) {
                        Inventory inventory = plugin.getInventoryUtil().createPanelInventory(player);
                        plugin.getProfileCache().getProfile(player).setSettingsInventory(inventory);
                        player.openInventory(inventory);
                    } else {
                        player.openInventory(plugin.getProfileCache().getProfile(player).getSettingsInventory());
                    }
                }

                if (stack.getItemMeta().getDisplayName().equals("Freundesanfragen")) {
                    player.openInventory(plugin.getInventoryUtil().createFriendRequestInventory(player));
                }

                if (event.getSlot() == 47 || event.getSlot() == 51) return;
                if (stack.getType().equals(Material.PLAYER_HEAD) || (stack.getType().equals(Material.SKELETON_SKULL))) {
                    player.openInventory(plugin.getInventoryUtil().
                            loadActionInventory(displayName, stack));
                }
                break;
            case "Waehle eine Lobby":
                IClusterSpigot spigot = Cloud.getInstance().getSpigotByDisplayName(displayName);
                if (spigot == null) {
                    player.sendMessage("§cDieser Server ist nicht online");
                }

                if (cloudPlayer.getSpigot().getDisplayName().equals(spigot.getDisplayName())) {
                    player.sendMessage("§cDu befindest dich schon auf dem Server");
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
                            if (stack.getType().equals(Material.PLAYER_HEAD)) {
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
                        player.closeInventory();
                        cloudPlayer.setField("tos", System.currentTimeMillis());
                        break;
                    case "Ablehnen":
                        cloudPlayer.kick("§cUm auf dem §f§oI§fce§3V§fizion.de §cNetzwerk spielen zu können,\n"+
                                "musst du unsere Nutzungsbedingungen akzeptieren!\n\n§ahttps://icevizion.de/tos-server/");
                        break;
                }
                break;
        }

        if (friendPattern.matcher(event.getView().getTitle()).find()) {
            if (stack.getType().equals(Material.AIR)) return;

            if (cloudPlayer == null)  {
                player.sendMessage("§cEs trat ein technischer Fehler auf");
            } else {
                handleAction(cloudPlayer, event.getClickedInventory().getItem(9),
                        event.getClickedInventory().getItem(9).getItemMeta().getDisplayName(),
                        ChatColor.stripColor(stack.getItemMeta().getDisplayName()));
            }
            player.closeInventory();
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTitle().equals("Nutzungsbedingungen")) {
            Bukkit.getScheduler().runTaskLater(plugin, () ->
                    event.getPlayer().openInventory(plugin.getInventoryUtil().getPrivacy()), 10);
        }
    }

    private void handleAction(CloudPlayer cloudPlayer, ItemStack stack, String name, String displayName) {
        LobbyProfile profile = plugin.getProfileCache().getProfile(cloudPlayer.getPlayer());
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
                //Inventory updaten
                profile.getFriendInventory().remove(stack);
                break;
            default:
                break;
        }
    }
}