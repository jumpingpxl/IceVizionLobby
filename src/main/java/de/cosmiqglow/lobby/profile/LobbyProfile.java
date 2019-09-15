package de.cosmiqglow.lobby.profile;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyProfile {

    private final Player player;
    private Inventory settingsInventory;
    private Inventory friendInventory;
    private int hideSettings;

    public LobbyProfile(Player player) {
        this.player = player;
    }

    public void setSettingsInventory(Inventory settingsInventory) {
        this.settingsInventory = settingsInventory;
    }

    public void setFriendInventory(Inventory friendInventory) {
        this.friendInventory = friendInventory;
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
}
