package de.icevizion.lobby.utils.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class SettingsChangeEvent extends PlayerEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final int category;
    private final int value;

    public SettingsChangeEvent(Player who, int category, int value) {
        super(who);
        this.category = category;
        this.value = value;
    }

    public int getCategory() {
        return category;
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
