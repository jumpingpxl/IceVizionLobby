package de.icevizion.lobby.profile;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyProfile {

    private final Player player;
    private Inventory settingsInventory;
    private Inventory friendInventory;
    private Inventory teleporterInventory;
    private int hideSettings;
    private String clickedFriend;

    public LobbyProfile(Player player) {
        this.player = player;
        this.hideSettings = 0;
    }

    public void setTeleporterInventory(Inventory teleporterInventory) {
        this.teleporterInventory = teleporterInventory;
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

    public void setClickedFriend(String clickedFriend) {
        this.clickedFriend = clickedFriend;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getTeleporterInventory() {
        return teleporterInventory;
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

    public String getClickedFriend() {
        return "Einstellungen für " + clickedFriend;
    }
}
