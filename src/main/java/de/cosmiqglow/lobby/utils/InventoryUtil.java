package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.ItemBuilder;
import de.cosmiqglow.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InventoryUtil {

    private final Lobby plugin;
    private Inventory teleporter;

    public InventoryUtil(Lobby plugin) {
        this.plugin = plugin;
        this.loadTeleporter();
    }

    public void loadTeleporter() {
        this.teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        teleporter.setItem(10, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayName("§eMiniUHC").build());
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

    public Inventory getTeleporter() {
        return teleporter;
    }
}
