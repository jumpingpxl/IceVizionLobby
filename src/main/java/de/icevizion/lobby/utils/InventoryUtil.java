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
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryUtil {

    private final ItemUtil itemUtil;
    private final SettingsUtil settingsUtil;

    public InventoryUtil(ItemUtil itemUtil, SettingsUtil settingsUtil) {
        this.itemUtil = itemUtil;
        this.settingsUtil = settingsUtil;
    }

    public Inventory loadTeleporter(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Minispiele");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getTeleporterLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        if (!cloudPlayer.hasPermission("network.buildserver")) {
            inventory.remove(inventory.getItem(26));
        }

        return inventory;
    }

    public Inventory loadTeleporterInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Minispiele");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getTeleporterLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        if (!cloudPlayer.hasPermission("network.buildserver")) {
            inventory.remove(inventory.getItem(26));
        }

        int slot = 0;
        switch (cloudPlayer.getSpigot().getDisplayName()) {
            case "Lobby-1":
                slot = 39;
                break;
            case "Lobby-2":
                slot = 40;
                break;
            case "Lobby-3":
                slot = 41;
                break;
            default:
                slot = -1;
                break;
        }

        if (slot >= 0) {
            inventory.setItem(slot,
                    new ItemBuilder(inventory.getItem(slot))
                            .addUnsafeEnchantment(Enchantment.DURABILITY, 1)
                            .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                            .build());
        }

        player.openInventory(inventory);

        return inventory;
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
        int friend = cloudPlayer.getSetting(SettingsUtil.FRIENDS);
        int jump = cloudPlayer.getSetting(SettingsUtil.JUMP);

        settingsUtil.setState(inventory, 0, privateMessage,false);
        settingsUtil.setState(inventory, 9, party, false);
        settingsUtil.setState(inventory, 18, friend, false);
        settingsUtil.setState(inventory, 27, jump, false);
        return inventory;
    }

    public Inventory createFriendInvenotory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Freunde");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getFriendLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(Cloud.getInstance().getPlayer(player));

        for (CloudPlayer cloudPlayer : friendProfile.getFriends()) {
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
}