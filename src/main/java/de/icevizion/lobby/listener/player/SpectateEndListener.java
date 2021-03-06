package de.icevizion.lobby.listener.player;

import de.icevizion.component.spectatorsystem.spigot.event.SpectateEndEvent;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SpectateEndListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    public SpectateEndListener(LobbyPlugin lobbyPlugin) {
        this.lobbyPlugin= lobbyPlugin;
    }

    @EventHandler
    public void onEnd(SpectateEndEvent event) {
        CloudPlayer cloudPlayer = lobbyPlugin.getCloudService().getPlayer(event.getPlayer());
        lobbyPlugin.getItems().setItems(cloudPlayer);
    }
}
