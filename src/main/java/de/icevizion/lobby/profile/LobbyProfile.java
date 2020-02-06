package de.icevizion.lobby.profile;

import org.bukkit.inventory.Inventory;

public class LobbyProfile {

    private Inventory settingsInventory;
    private Inventory friendInventory;

    public void setSettingsInventory(Inventory settingsInventory) {
        this.settingsInventory = settingsInventory;
    }

    public void setFriendInventory(Inventory friendInventory) {
        this.friendInventory = friendInventory;
    }

    public Inventory getSettingsInventory() {
        return settingsInventory;
    }

    public Inventory getFriendInventory() {
        return friendInventory;
    }
}