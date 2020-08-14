package de.icevizion.lobby.listener;

import org.bukkit.event.Listener;

public class PlayerInventoryListener implements Listener {

/*    private final LobbyPlugin plugin;

    public PlayerInventoryListener(LobbyPlugin plugin) {
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


        event.setResult(Event.Result.DENY);
        event.setCancelled(true);


        //Don´t remove this line of code
        player.updateInventory();

        String displayName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

        switch (event.getView().getTitle()) {
            case "Einstellungen":
                plugin.getSettingsUtil().changeSettingsValue(cloudPlayer, event.getInventory(), stack, event.getSlot());
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

                if (displayName.equals("Freundesanfragen")
                        && FriendSystem.getInstance().getFriendProfile(cloudPlayer).getRequests().size() != 0) {
                    player.openInventory(plugin.getInventoryUtil().createFriendRequestInventory(cloudPlayer));
                }

                if (stack.getType().equals(Material.SKULL_ITEM)) {
                    player.openInventory(plugin.getInventoryUtil().loadActionInventory(displayName, stack));
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
    } */
}