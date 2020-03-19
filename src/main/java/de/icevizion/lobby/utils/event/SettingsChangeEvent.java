package de.icevizion.lobby.utils.event;

import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SettingsChangeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final CloudPlayer cloudPlayer;
    private final int setting;
    private final int value;

    public SettingsChangeEvent(CloudPlayer who, int setting, int value) {
        this.cloudPlayer = who;
        this.setting = setting;
        this.value = value;
    }

    public CloudPlayer getPlayer() {
        return cloudPlayer;
    }

    public int getSetting() {
        return setting;
    }

    public int getValue() {
        return value;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}