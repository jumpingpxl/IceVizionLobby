package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import de.icevizion.lobby.utils.event.SettingsChangeEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SettingsUtil {

    private static final int CLICK_OFFSET = 6;

    protected static final int PRIVAT_MESSAGE = 100;
    protected static final int PARTY = 101;
    public static final int PLAYER_VISIBILITY = 102;
    protected static final int JUMP = 103;
    public static final int SPAWN_LOCATION = 104;
    public static final int EVENT = 199;
    public static final int NICK = 104;

    /**
     * Changes the current state of a setting.
     * @param cloudPlayer The player who changed something
     * @param inventory The used inventory
     * @param itemStack The clicked {@link ItemStack}
     * @param slot The clicked slot from the inventory
     */

    public void changeSettingsValue(CloudPlayer cloudPlayer, Inventory inventory, ItemStack itemStack, int slot) {
        if (itemStack.getType().equals(Material.GRAY_DYE)) {
            int currentRow = slot / 9;
            int category = currentRow * 9;
            int newValue = slot - category - CLICK_OFFSET;
            int oldVal = -1;
            if (currentRow >= 3) {
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
            cloudPlayer.getPlayer().updateInventory();
            cloudPlayer.setSetting(getSettingsID(currentRow), newValue);

            Bukkit.getPluginManager().callEvent(new SettingsChangeEvent(cloudPlayer,
                    getSettingsID(currentRow), newValue));
        }
    }

    /**
     * Updates the state of a specific {@link ItemStack}.
     * @param inv The used inventory
     * @param category The category where something should be changed
     * @param value The new value
     * @param gray If the new state is gray or not
     */

    public void setState(Inventory inv, int category , int value, boolean gray) {
        ItemStack state;
        switch (value) {
            case 0:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.LIME_DYE).
                        setDisplayName("§aAlle").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 1:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ORANGE_DYE).
                        setDisplayName(category == 4 ? "§aAn" : "§6Freunde").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 2:
                state = new ItemBuilder(gray ? Material.GRAY_DYE : Material.ROSE_RED).
                        setDisplayName(category == 4 ? "§cAus" : "§cKeiner").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
        }
    }

    private int setForState(int newValue, Inventory inventory, int category, int forInt, int offset) {
        for (int i = offset; i < forInt; i++) { // Such ALG
            if (i != newValue && !inventory.getItem(category + (CLICK_OFFSET + i)).
                    getType().equals(Material.GRAY_DYE)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Wraps the given row to the right database id of the setting.
     * @param row The clicked row to convert
     * @return The settings id for the database
     */

    private int getSettingsID(int row) {
        switch (row) {
            case 0:
                return PRIVAT_MESSAGE;
            case 1:
                return PARTY;
            case 2:
                return PLAYER_VISIBILITY;
            case 3:
                return JUMP;
            case 4:
                return SPAWN_LOCATION;
            default:
                return -1;
        }
    }
}