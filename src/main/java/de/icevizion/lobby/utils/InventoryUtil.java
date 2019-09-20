package de.icevizion.lobby.utils;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
import de.cosmiqglow.aves.item.ItemBuilder;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Map;

public class InventoryUtil {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    private final ItemUtil itemUtil;
    private final SettingsUtil settingsUtil;
    private Inventory teleporter;

    public InventoryUtil(ItemUtil itemUtil, SettingsUtil settingsUtil) {
        this.itemUtil = itemUtil;
        this.settingsUtil = settingsUtil;
        this.loadTeleporter();
    }

    private void loadTeleporter() {
        this.teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        teleporter.setItem(10, new ItemBuilder(Material.CHEST_MINECART).setDisplayName("§eCargoEscort").build());
        teleporter.setItem(13, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§aSpawn").build());
        teleporter.setItem(16, new ItemBuilder(Material.STICK).setDisplayName("§bKnockbackFFA").build());
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

        int privatMessage = cloudPlayer.getSetting(SettingsUtil.PRIVAT_MESSAGE);
        int party = cloudPlayer.getSetting(SettingsUtil.PARTY);
        int friend = cloudPlayer.getSetting(SettingsUtil.FRIENDS);
        int jump = cloudPlayer.getSetting(SettingsUtil.JUMP);

        settingsUtil.setState(inventory, 0, privatMessage,false);
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

        int requests = friendProfile.getRequests().size();
        ItemStack stack = inventory.getItem(45);

        for (CloudPlayer cloudPlayer : friendProfile.getFriends()) {
            if (cloudPlayer.isOnline()) {
                inventory.addItem(new CustomPlayerHeadBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .addLore("§7Befindet sich auf: §e" + cloudPlayer.getSpigot().getDisplayName())
                        .setDisplayName(cloudPlayer.getFullUsername()).build());
            } else {
                inventory.addItem(new ItemBuilder(Material.SKELETON_SKULL)
                        .setDisplayName(cloudPlayer.getFullUsername())
                        .addLore("§7Zuletzt Online: §e" + DATE_FORMAT.format(cloudPlayer.getLastLogout())).build());
            }
        }
        return inventory;
    }

    public Inventory getTeleporter() {
        return teleporter;
    }
}
