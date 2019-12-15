package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.LobbyUtil;
import net.titan.spigot.event.*;
import org.bukkit.entity.Player;
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
            lobbyUtil.updateSlots();
            lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
        }
    }

    @EventHandler
    public void onUnavailable(SpigotUnavailableEvent event) {
        if (event.getSpigot().getDisplayName().startsWith("Lobby")) {
            lobbyUtil.updateSlots();
            lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
        }
    }

    @EventHandler
    public void onSwitch(NetworkPlayerServerSwitchedEvent event) {
        if (event.getNewServer().getDisplayName().startsWith("Lobby") || event.getOldServer().getDisplayName().startsWith("Lobby")) {
            lobbyUtil.updateSlots();
            lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
        }
    }

    @EventHandler
    public void onJoin(NetworkPlayerJoinEvent event) {

    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {

    }
}
