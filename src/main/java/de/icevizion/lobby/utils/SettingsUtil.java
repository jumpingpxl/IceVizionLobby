package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsUtil {

    private static final int CLICK_OFFSET = 6;
    protected static final int PRIVAT_MESSAGE = 100;
    protected static final int PARTY = 101;
    protected static final int FRIENDS = 102;
    protected static final int JUMP = 103;
    public static final int NICK = 104;

    public void changeSettingsValue(Player player, Inventory inventory, ItemStack itemStack, int slot) {
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        if (itemStack.getType().equals(Material.GRAY_DYE)) {
            int currentRow = slot / 9;
            int category = currentRow * 9;
            int newValue = slot - category - CLICK_OFFSET;
            int oldVal = -1;
            if (currentRow >= 2) {
                oldVal = setForState(newValue, inventory, category,2, 1);
            } else {
                oldVal = setForState(newValue, inventory, category,3, 0);
            }

            if (oldVal == -1) {
                setState(inventory, category, 2, true);
            } else {
                setState(inventory, category, oldVal, true);
            }

            setState(inventory, category, newValue, false);
            player.updateInventory();
            cloudPlayer.setSetting(getSettingsID(currentRow), newValue);
        }
    }

    public void setState(Inventory inv, int category , int value, boolean gray) {
        ItemStack state;
        switch (value) {
            case 0:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.LIME_DYE).setDisplayName("§fVon jedem").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 1:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ORANGE_DYE).setDisplayName("§fVon Freunden").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 2:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ROSE_RED).setDisplayName("§fVon Niemanden").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
        }
    }

    private int setForState(int newValue, Inventory inventory, int category, int forInt, int offset) {
        for (int i = offset; i < forInt; i++) {
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
            case 4:
                return NICK;
            default:
                return -1;
        }
    }
}