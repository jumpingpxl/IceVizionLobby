package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ColoredBuilder;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Colorable;
import org.bukkit.material.Dye;
@Deprecated
public class SettingsUtil {

    private final VisibilityTool visibilityTool;
    private static final int CLICK_OFFSET = 6;

    public SettingsUtil(VisibilityTool visibilityTool) {
        this.visibilityTool = visibilityTool;
    }

    /**
     * Changes the current state of a setting.
     * @param cloudPlayer The player who changed something
     * @param inventory The used inventory
     * @param itemStack The clicked {@link ItemStack}
     * @param slot The clicked slot from the inventory
     */

    public void changeSettingsValue(CloudPlayer cloudPlayer, Inventory inventory, ItemStack itemStack, int slot) {
        if (itemStack.getData() instanceof Dye && ((Colorable)itemStack.getData()).getColor() == DyeColor.GRAY) {
            int currentRow = slot / 9;
            int category = currentRow * 9;
            int newValue = slot - category - CLICK_OFFSET;
            SettingsWrapper wrapper = getSetting(currentRow);
            int oldVal = setForState(newValue, inventory, category, wrapper.getValue(), wrapper.getValue() == 3 ? 0 : 1);
            if (oldVal == -1) {
                setState(inventory, category, 2, true);
            } else {
                setState(inventory, category, oldVal, true);
            }

            setState(inventory, category, newValue, false);
            cloudPlayer.getPlayer().updateInventory();
            cloudPlayer.setSetting(wrapper.getID(), newValue);

            handleSettingsChange(cloudPlayer, wrapper, newValue);
        }
    }

    public void handleSettingsChange(CloudPlayer cloudPlayer, SettingsWrapper wrapper, int newValue) {
        if (wrapper == SettingsWrapper.PLAYER_VISIBILITY) {
            visibilityTool.changeVisibility(cloudPlayer, newValue);
        }
    }

    /**
     * Updates the state of a specific {@link ItemStack}.
     * @param inv The used inventory
     * @param category The category where something should be changed
     * @param value The new value
     * @param gray If the new state is gray or not
     */

    public void setState(Inventory inv, int category, int value, boolean gray) {
        ItemStack state;
        switch (value) {
            case 0:
                state = new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(gray ? DyeColor.GRAY : DyeColor.LIME)
                        .setDisplayName("§aAlle").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 1:
                state = new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(gray ? DyeColor.GRAY : DyeColor.ORANGE)
                        .setDisplayName(category == 4 ? "§aAn" : "§6Freunde").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
                break;
            case 2:
                state = new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(gray ? DyeColor.GRAY : DyeColor.RED)
                        .setDisplayName(category == 4 ? "§cAus" : "§cKeiner").build();
                inv.setItem(category + CLICK_OFFSET + value, state);
        }
    }

    private int setForState(int newValue, Inventory inventory, int category, int forInt, int offset) {
        for (int i = offset; i < forInt; i++) {
            if (i != newValue && !(inventory.getItem(category + CLICK_OFFSET + i).getData() instanceof Dye &&
                    ((Colorable)inventory.getItem(category + CLICK_OFFSET + i)
                            .getData()).getColor() == DyeColor.GRAY)) {
                return i;
            }
        }
        return -1;
    }

    private SettingsWrapper getSetting(int row) {
        switch (row) {
            case 0:
                return SettingsWrapper.PRIVATE_MESSAGE;
            case 1:
                return SettingsWrapper.PARTY;
            case 2:
                return SettingsWrapper.PLAYER_VISIBILITY;
            case 3:
                return SettingsWrapper.JUMP;
            default:
                return null;
        }
    }
}