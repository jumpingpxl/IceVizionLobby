package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.ItemBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsUtil {

    public static final int PRIVAT_MESSAGE = 100;
    public static final int PARTY = 101;
    public static final int FRIENDS = 102;
    public static final int JUMP = 103;

    public void changeSettingsValue(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer((Player) event.getWhoClicked());
        if (event.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
            int currentRow = event.getSlot() / 9;
            int category = currentRow * 9;
            int newValue = (event.getSlot() - category) - 3;
            int oldVal = -1;
            for (int i = 0; i < 3; i++) {
                if (i == newValue) continue;
                if (!inventory.getItem(category + ( 3 + i)).getType().equals(Material.GRAY_DYE)) {
                    oldVal = i;
                }
            }
            setState(inventory, category, oldVal, true);
            setState(inventory, category, newValue, false);
            ((Player) event.getWhoClicked()).updateInventory();
            cloudPlayer.setSetting(getSettingsID(currentRow), newValue);
        }
    }

    public void setState(Inventory inv, int category , int value, boolean gray) {
        ItemStack state;
        switch (value) {
            case 0:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.LIME_DYE).setDisplayName("§fVon jedem").build();
                inv.setItem(category + ( 3 + value), state);
                break;
            case 1:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ORANGE_DYE).setDisplayName("§fVon Freunden").build();
                inv.setItem(category + ( 3 + value), state);
                break;
            case 2:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ROSE_RED).setDisplayName("§fVon Niemanden").build();
                inv.setItem(category + ( 3 + value), state);
                break;
        }
    }

    private int getSettingsID(int row) {
        switch (row) {
            case 0:
                return PRIVAT_MESSAGE;
            case 1:
                return PARTY;
            case 2:
                return FRIENDS;
            case 3:
                return JUMP;
            default:
                return -1;
        }
    }
}