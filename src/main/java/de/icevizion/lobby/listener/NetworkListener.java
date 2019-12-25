package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.LobbyUtil;
import net.titan.spigot.event.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NetworkListener implements Listener {

    private final LobbyUtil lobbyUtil;
    private final Lobby lobby;

    public NetworkListener(LobbyUtil lobbyUtil, Lobby lobby) {
        this.lobbyUtil = lobbyUtil;
        this.lobby = lobby;
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
        //Delay this update because the event is too fast and sometimes redis has not the right data yet
        Bukkit.getScheduler().runTaskLater(lobby, () -> {
            lobbyUtil.updateSlots();
            lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
        }, 2);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {

    }
}
