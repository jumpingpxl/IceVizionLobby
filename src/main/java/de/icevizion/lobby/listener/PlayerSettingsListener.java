package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.event.SettingsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSettingsListener implements Listener {

    @EventHandler
    public void onSettings(SettingsChangeEvent event) {
        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " " + event.getCategory());
    }
}
