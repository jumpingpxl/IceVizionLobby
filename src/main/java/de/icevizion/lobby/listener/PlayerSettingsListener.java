package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.event.SettingsChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSettingsListener implements Listener {

    private final Lobby plugin;

    public PlayerSettingsListener(Lobby plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSettings(SettingsChangeEvent event) {
        switch (event.getSetting()) {
            /*case SettingsUtil.PLAYER_VISIBILITY:
                Bukkit.broadcastMessage("Value" + event.getValue());
                plugin.getVisibilityUtil().changeVisibility(plugin, Cloud.getInstance().getPlayer(event.getPlayer())
                        , event.getValue());
                break;*/
        }
    }
}
