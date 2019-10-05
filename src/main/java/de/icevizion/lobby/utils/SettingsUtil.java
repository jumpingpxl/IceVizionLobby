package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsUtil {

    private static final int CLICK_OFFSET = 6;

    public static final int PRIVAT_MESSAGE = 100;
    public static final int PARTY = 101;
    public static final int FRIENDS = 102;
    public static final int JUMP = 103;
    public static final int NICK = 104;

    public void changeSettingsValue(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer((Player) event.getWhoClicked());
        if (event.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
            int currentRow = event.getSlot() / 9;
            int category = currentRow * 9;
            int newValue = (event.getSlot() - category) - CLICK_OFFSET;
            int oldVal = -1;
            if (currentRow >= 2) {
                oldVal = setForState(newValue, inventory, category,2, 1);
            } else {
                oldVal = setForState(newValue, inventory, category,3, 0);
            }

            if(oldVal == -1) {
                setState(inventory, category, 2, true);
            } else {
                setState(inventory, category, oldVal, true);
            }

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
                inv.setItem(category + ( CLICK_OFFSET + value), state);
                break;
            case 1:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ORANGE_DYE).setDisplayName("§fVon Freunden").build();
                inv.setItem(category + ( CLICK_OFFSET + value), state);
                break;
            case 2:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ROSE_RED).setDisplayName("§fVon Niemanden").build();
                inv.setItem(category + ( CLICK_OFFSET + value), state);
                break;
        }
    }

    private int  setForState(int newValue, Inventory inventory, int category, int forInt, int offset) {
        for (int i = offset; i < forInt; i++) { // Such ALG
            if (i == newValue) continue;
            if (!inventory.getItem(category + ( CLICK_OFFSET + i)).getType().equals(Material.GRAY_DYE)) {
                return i;
            }
        }
        return -1;
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