package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.aves.item.SkullBuilder;
import de.icevizion.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public class InventoryUtil {

    private final Lobby plugin;
    private Inventory teleporter;
    private Inventory privacy;

    public InventoryUtil(Lobby plugin) {
        this.plugin = plugin;
        this.loadTeleporter();
        this.loadPrivacy();
    }

    public void loadTeleporter() {
        teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        teleporter.setItem(4, new ItemBuilder(Material.BOOK_AND_QUILL).setDisplayName("§bGuessIt").build());
        teleporter.setItem(11, new ItemBuilder(Material.STICK).setDisplayName("§aKnockbackFFA").build());
        teleporter.setItem(13, new ItemBuilder(Material.NETHER_STAR).setDisplayName("§aSpawn").build());
        teleporter.setItem(15, new ItemBuilder(Material.SANDSTONE).setDisplayName("§eOneLine").build());
        teleporter.setItem(22, new ItemBuilder(Material.BED).setDisplayName("§2BedWars").build());
    }

    private void loadPrivacy() {
        privacy = Bukkit.createInventory(null, 27, "Nutzungsbedingungen");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendSubLayout().entrySet()) {
            privacy.setItem(entry.getKey(), entry.getValue());
        }
        privacy.setItem(9, new ItemBuilder(Material.BOOK).setDisplayName("§aNutzungsbedingungen").
                addLore("§6icevizion.de/tos-server").build());
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

        int privateMessage = player.getSetting(SettingsWrapper.PRIVATE_MESSAGE.getID());
        int party = player.getSetting(SettingsWrapper.PARTY.getID());
        int friend = player.getSetting(SettingsWrapper.PLAYER_VISIBILITY.getID());
        int jump = player.getSetting(SettingsWrapper.JUMP.getID());

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

        if (friendProfile.getRequests().size() == 0) {
            inventory.setItem(13, new ItemBuilder(Material.BARRIER).
                    setDisplayName("§cKeiner möchte mit dir befreundet sein :(").build());
        } else {
            for (CloudPlayer request : friendProfile.getRequests()) {
                inventory.addItem(new SkullBuilder()
                        .setSkinOverValues(request.getSkinValue(), "")
                        .setDisplayName(request.getFullDisplayName())
                        .build());
            }
        }
        return inventory;
    }

    public Inventory createFriendInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Freunde");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        List<CloudPlayer> sortedFriends = plugin.getFriendUtil().sortPlayers(Cloud.getInstance().getPlayer(player));
        for (CloudPlayer cloudPlayer : sortedFriends) {
            if (cloudPlayer.isOnline()) {
                inventory.addItem(new SkullBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + cloudPlayer.getSpigot().getDisplayName())
                        .setDisplayName(cloudPlayer.getFullUsername()).build());
            } else {
                inventory.addItem(new SkullBuilder(SkullBuilder.SkullType.SKELETON)
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