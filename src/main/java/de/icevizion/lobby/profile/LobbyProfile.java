package de.icevizion.lobby.profile;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyProfile {

    private final Player player;
    private Inventory settingsInventory;
    private Inventory friendInventory;
    private boolean settingsUse;

    public LobbyProfile(Player player) {
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public Inventory getSettingsInventory() {
        return settingsInventory;
    }

    public Inventory getFriendInventory() {
        return friendInventory;
    }

    public boolean isSettingsUse() {
        return settingsUse;
    }
}
