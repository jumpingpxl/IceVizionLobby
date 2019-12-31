package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.CustomPlayerHeadBuilder;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class InventoryUtil {

    private final ItemUtil itemUtil;
    private final SettingsUtil settingsUtil;

    private Inventory teleporter;
    private Inventory privacy;

    public InventoryUtil(ItemUtil itemUtil, SettingsUtil settingsUtil) {
        this.itemUtil = itemUtil;
        this.settingsUtil = settingsUtil;
        this.loadTeleporter();
        this.loadPrivacy();
    }

    public void loadTeleporter() {
        teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getTeleporterLayout().entrySet()) {
            teleporter.setItem(entry.getKey(), entry.getValue());
        }
    }

    private void loadPrivacy() {
        privacy = Bukkit.createInventory(null, 27, "Datenschutz");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendSubLayout().entrySet()) {
            privacy.setItem(entry.getKey(), entry.getValue());
        }
    }

    public Inventory loadActionInventory(String name, ItemStack skull) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Einstellungen für " + name);
        inventory.setItem(9, skull);
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendActionLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }

    public Inventory createPanelInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Einstellungen");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getSettingsLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        int privateMessage = cloudPlayer.getSetting(SettingsUtil.PRIVAT_MESSAGE);
        int party = cloudPlayer.getSetting(SettingsUtil.PARTY);
        int friend = cloudPlayer.getSetting(SettingsUtil.PLAYER_VISIBILITY);
        int jump = cloudPlayer.getSetting(SettingsUtil.JUMP);

        settingsUtil.setState(inventory, 0, privateMessage,false);
        settingsUtil.setState(inventory, 9, party, false);
        settingsUtil.setState(inventory, 18, friend, false);
        settingsUtil.setState(inventory, 27, jump, false);
        return inventory;
    }

    public Inventory createAcceptInventory(String name, ItemStack skull) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Anfrage von " + name);

        inventory.setItem(9, skull);
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendSubLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }

    public Inventory createFriendRequestInventory(Player player) {
        Inventory inventory =  Bukkit.createInventory(null, 54, "Freundesanfragen");

        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(Cloud.getInstance().getPlayer(player));

        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendRequests().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        for (CloudPlayer request : friendProfile.getRequests()) {
            inventory.addItem(new CustomPlayerHeadBuilder()
                    .setSkinOverValues(request.getSkinValue(), "")
                    .setDisplayName(request.getFullDisplayName())
                    .build());
        }
        return inventory;
    }

    public Inventory createFriendInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Freunde");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(Cloud.getInstance().getPlayer(player));
        List<CloudPlayer> sortedFriends = friendProfile.getFriends();
        sortedFriends.sort((cp1, cp2) -> {
            if (cp1.isOnline() && cp2.isOnline())
                return 0;
            if (cp1.isOnline())
                return -1;
            return 1;
        });

        for (CloudPlayer cloudPlayer : sortedFriends) {
            if (cloudPlayer.isOnline()) {
                inventory.addItem(new CustomPlayerHeadBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + cloudPlayer.getSpigot().getDisplayName())
                        .setDisplayName(cloudPlayer.getFullUsername()).build());
            } else {
                inventory.addItem(new ItemBuilder(Material.SKELETON_SKULL)
                        .setDisplayName(cloudPlayer.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + Lobby.DATE_FORMAT.format(cloudPlayer.getLastLogout())).build());
            }
        }
        return inventory;
    }

    /**
     * Returns the inventory for the teleporter
     * @return The Inventory for the teleporter
     */

    public Inventory getTeleporter() {
        return teleporter;
    }

    /**
     * Returns the inventory for the privacy query
     * @return The inventory for privacy
     */

    public Inventory getPrivacy() {
        return privacy;
    }
}