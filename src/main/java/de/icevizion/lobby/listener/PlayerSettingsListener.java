package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.SettingsUtil;
import de.icevizion.lobby.utils.event.SettingsChangeEvent;
import net.titan.spigot.Cloud;
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
            case SettingsUtil.PLAYER_VISIBILITY:
                plugin.getVisibilityUtil().changeVisibility(plugin, event.getPlayer(), event.getValue());
                break;
        }
    }
}
