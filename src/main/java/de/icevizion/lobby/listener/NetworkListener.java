package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.LobbyUtil;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.SpigotAvailableEvent;
import net.titan.spigot.event.SpigotUnavailableEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NetworkListener implements Listener {

    private final LobbyUtil lobbyUtil;

    public NetworkListener(LobbyUtil lobbyUtil) {
        this.lobbyUtil = lobbyUtil;
    }

    @EventHandler
    public void onAvailable(SpigotAvailableEvent event) {
        if (event.getSpigot().getDisplayName().startsWith("Lobby")) {
            lobbyUtil.addLobby(event.getSpigot());
        }
    }

    @EventHandler
    public void onUnavailable(SpigotUnavailableEvent event) {
        if (event.getSpigot().getDisplayName().startsWith("Lobby")) {
            lobbyUtil.removeLobby(event.getSpigot());
        }
    }

    @EventHandler
    public void onJoin(NetworkPlayerJoinEvent event) {

    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {

    }
}
