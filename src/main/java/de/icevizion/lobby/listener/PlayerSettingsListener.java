package de.icevizion.lobby.listener;

import de.icevizion.lobby.feature.SnowService;
import de.icevizion.lobby.utils.event.SettingsChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSettingsListener implements Listener {

    private final SnowService snowService;

    public PlayerSettingsListener(SnowService snowService) {
        this.snowService = snowService;
    }

    @EventHandler
    public void onSettings(SettingsChangeEvent event) {
        switch (event.getSetting()) {
            case 199:
                if (event.getValue() == 1) {
                    snowService.addPlayer(event.getPlayer());
                } else {
                    snowService.removePlayer(event.getPlayer());
                }
                break;
        }
        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + " " + event.getSetting());
    }
}
