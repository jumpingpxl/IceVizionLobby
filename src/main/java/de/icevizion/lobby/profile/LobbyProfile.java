package de.icevizion.lobby.profile;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyProfile {

    private final Player player;
    private Inventory settingsInventory;
    private Inventory friendInventory;
    private int hideSettings;

    private boolean settingsUse;

    public LobbyProfile(Player player) {
        this.player = player;
        this.hideSettings = 0;
        this.settingsUse = true;
    }

    public void setSettingsInventory(Inventory settingsInventory) {
        this.settingsInventory = settingsInventory;
    }

    public void setFriendInventory(Inventory friendInventory) {
        this.friendInventory = friendInventory;
    }

    public void setSettingsUse(boolean settingsUse) {
        this.settingsUse = settingsUse;
    }

    public void setHideSettings(int hideSettings) {
        this.hideSettings = hideSettings;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getSettingsInventory() {
        return settingsInventory;
    }

    public Inventory getFriendInventory() {
        return friendInventory;
    }

    public int getHideSettings() {
        return hideSettings;
    }

    public boolean isSettingsUse() {
        return settingsUse;
    }
}
