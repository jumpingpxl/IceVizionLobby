package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
import de.cosmiqglow.aves.item.ItemBuilder;
import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.cosmiqglow.lobby.Lobby;
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

    private final Lobby plugin;
    private Inventory teleporter;

    public InventoryUtil(Lobby plugin) {
        this.plugin = plugin;
        this.loadTeleporter();
    }

    public void loadTeleporter() {
        this.teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        teleporter.setItem(10, new ItemBuilder(Material.CHEST_MINECART).setDisplayName("§eCargoEscort").build());
        teleporter.setItem(13, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§aSpawn").build());
        teleporter.setItem(16, new ItemBuilder(Material.STICK).setDisplayName("§bKnockbackFFA").build());
    }

    public Inventory createPanelInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Einstellungen");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getSettingsLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        int privatMessage = cloudPlayer.getSetting(SettingsUtil.PRIVAT_MESSAGE);
        int party = cloudPlayer.getSetting(SettingsUtil.PARTY);
        int friend = cloudPlayer.getSetting(SettingsUtil.FRIENDS);
        int jump = cloudPlayer.getSetting(SettingsUtil.JUMP);

        plugin.getSettingsUtil().setState(inventory, 0, privatMessage,false);
        plugin.getSettingsUtil().setState(inventory, 9, party, false);
        plugin.getSettingsUtil().setState(inventory, 18, friend, false);
        plugin.getSettingsUtil().setState(inventory, 27, jump, false);
        return inventory;
    }

    public Inventory createFriendInvenotory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Freunde");
        for (Map.Entry<Integer, ItemStack> entry : plugin.getItemUtil().getFriendLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(Cloud.getInstance().getPlayer(player));

        int requests = friendProfile.getRequests().size();
        ItemStack stack = inventory.getItem(45);

        Bukkit.broadcastMessage("Requests: " + requests);
        /*
        if (requests > 64) {
            inventory.setItem(45, new ItemBuilder(stack).
                    setDisplayName(stack.getItemMeta().getDisplayName()).
                    addLore("§cDu hast über 64 Anfragen").setAmount(64).build());
        } else {
            inventory.setItem(45, new ItemBuilder(stack).setDisplayName(stack.getItemMeta().getDisplayName()).
                    setAmount(requests).build());
        }*/

        for (CloudPlayer cloudPlayer : friendProfile.getFriends()) {
            if (cloudPlayer.isOnline()) {
                inventory.addItem(new CustomPlayerHeadBuilder()
                        .setSkinOverValues(cloudPlayer.getSkinValue(), "")
                        .setDisplayName(cloudPlayer.getDisplayColor() + cloudPlayer.getDisplayName())
                        .setAmount(1).build());
            } else {
                inventory.addItem(new ItemBuilder(Material.SKELETON_SKULL)
                        .setDisplayName("§7" + cloudPlayer.getDisplayName())
                        .addLore("§7Zuletzt Online: §e" + DATE_FORMAT.format(cloudPlayer.getLastLogout()))
                        .setAmount(1).build());
            }
        }
        return inventory;
    }

    public Inventory getTeleporter() {
        return teleporter;
    }
}
