package de.icevizion.lobby.utils;

@Deprecated
public class SettingsUtil {

/*

    public void changeSettingsValue(CloudPlayer cloudPlayer, Inventory inventory, ItemStack itemStack, int slot) {
        if (itemStack.getData() instanceof Dye && ((Colorable)itemStack.getData()).getColor() == DyeColor.GRAY) {

            //slot = 8

            int currentRow = slot / 9; //1
            int category = currentRow * 9; //9?
            int newValue = slot - category - CLICK_OFFSET; //-7????????
            Setting wrapper = getSetting(currentRow);
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

    public void handleSettingsChange(CloudPlayer cloudPlayer, Setting wrapper, int newValue) {
        if (wrapper == Setting.PLAYER_VISIBILITY) {
            visibilityTool.changeVisibility(cloudPlayer, newValue);
        }
    }

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

    private Setting getSetting(int row) {
        switch (row) {
            case 0:
                return Setting.PRIVATE_MESSAGE;
            case 1:
                return Setting.PARTY_INVITE;
            case 2:
                return Setting.PLAYER_VISIBILITY;
            case 3:
                return Setting.FRIEND_JUMP;
            default:
                return null;
        }
    } */
}