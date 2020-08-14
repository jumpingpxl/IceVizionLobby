package de.icevizion.lobby.utils;

@Deprecated
public class InventoryUtil {

/*    private final LobbyPlugin plugin;
    private Inventory teleporter;
    private Inventory privacy;

    public InventoryUtil(LobbyPlugin plugin) {
        this.plugin = plugin;
        this.loadTeleporter();
        this.loadPrivacy();
    }

    public Inventory loadActionInventory(String name, ItemStack skull) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Einstellungen für " + name);
        inventory.setItem(9, skull);
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendActionLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }

    public Inventory createPanelInventory(CloudPlayer player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Einstellungen");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getSettingsLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        int privateMessage = player.getSetting(Setting.PRIVATE_MESSAGE.getID());
        int party = player.getSetting(Setting.PARTY_INVITE.getID());
        int friend = player.getSetting(Setting.PLAYER_VISIBILITY.getID());
        int jump = player.getSetting(Setting.FRIEND_JUMP.getID());

        plugin.getSettingsUtil().setState(inventory, 0, privateMessage,false);
        plugin.getSettingsUtil().setState(inventory, 9, party, false);
        plugin.getSettingsUtil().setState(inventory, 18, friend, false);
        plugin.getSettingsUtil().setState(inventory, 27, jump, false);
        return inventory;
    }

    public Inventory createAcceptInventory(String name, ItemStack skull) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Anfrage von " + name);

        inventory.setItem(9, skull);
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendSubLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }

    public Inventory createFriendRequestInventory(CloudPlayer player) {
        Inventory inventory =  Bukkit.createInventory(null, 54, "Freundesanfragen");

        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(player);

        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendRequests().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        for (CloudPlayer request : friendProfile.getRequests()) {
            inventory.addItem(new SkullBuilder()
                    .setSkinOverValues(request.getSkinValue(), "")
                    .setDisplayName(request.getFullDisplayName())
                    .build());
        }
        return inventory;
    }

    public Inventory createFriendInventory(CloudPlayer player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Freunde");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        List<CloudPlayer> sortedFriends = plugin.getFriendUtil().sortPlayers(player);
        for (CloudPlayer cloudPlayer : sortedFriends) {
            if (cloudPlayer.isOnline()) {
                inventory.addItem(new SkullBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + cloudPlayer.getSpigot().getDisplayName())
                        .setDisplayName(cloudPlayer.getFullUsername()).build());
            } else {
                inventory.addItem(new SkullBuilder(SkullBuilder.SkullType.SKELETON)
                        .setDisplayName(cloudPlayer.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + LobbyPlugin.DATE_FORMAT.format(cloudPlayer.getLastLogout())).build());
            }
        }
        return inventory;
    }

    public Inventory getTeleporter() {
        return teleporter;
    }

    public Inventory getPrivacy() {
        return privacy;
    } */
}