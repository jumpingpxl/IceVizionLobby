package de.icevizion.lobby.listener.player;

import de.icevizion.component.spectatorsystem.spigot.event.SpectateEndEvent;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSpectatorListener implements Listener {

    private final LobbyPlugin lobbyPlugin;

    public PlayerSpectatorListener(LobbyPlugin lobbyPlugin) {
        this.lobbyPlugin= lobbyPlugin;
    }

    @EventHandler
    public void onEnd(SpectateEndEvent event) {
        CloudPlayer cloudPlayer = lobbyPlugin.getTitanService().getPlayer(event.getPlayer());
        lobbyPlugin.getItems().setItems(cloudPlayer);
    }
}
