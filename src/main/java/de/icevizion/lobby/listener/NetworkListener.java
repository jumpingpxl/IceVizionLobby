package de.icevizion.lobby.listener;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.LobbyUtil;
import net.titan.lib.redisevent.events.PlayerJoinEvent;
import net.titan.lib.redisevent.events.PlayerQuitEvent;
import net.titan.lib.redisevent.events.PlayerServerSwitchEvent;
import net.titan.lib.redisevent.events.ServerAvailableEvent;
import net.titan.lib.redisevent.events.ServerUnavailableEvent;
import net.titan.spigot.Cloud;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NetworkListener implements Listener {

    private final LobbyUtil lobbyUtil;
    private final Lobby lobby;

    public NetworkListener(LobbyUtil lobbyUtil, Lobby lobby) {
        this.lobbyUtil = lobbyUtil;
        this.lobby = lobby;

        initRedisEvents();
    }

    private void initRedisEvents() {
        Cloud.getInstance().getRedisEventManager().registerListener(PlayerJoinEvent.class, rEvent -> {
            PlayerJoinEvent joinEvent = (PlayerJoinEvent) rEvent;

            updateLobbySlots();
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerQuitEvent.class, rEvent -> {
            PlayerQuitEvent quitEvent = (PlayerQuitEvent) rEvent;

            updateLobbySlots();
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerServerSwitchEvent.class, rEvent -> {
            PlayerServerSwitchEvent switchEvent = (PlayerServerSwitchEvent) rEvent;

            //Delay this update because the event is too fast and sometimes redis has not the right data yet
            Bukkit.getScheduler().runTaskLater(lobby, () -> {
                updateLobbySlots();
            }, 5);
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerAvailableEvent.class, rEvent -> {
            ServerAvailableEvent availableEvent = (ServerAvailableEvent) rEvent;

            updateLobbySlots();
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerUnavailableEvent.class, rEvent -> {
            ServerUnavailableEvent unavailableEvent = (ServerUnavailableEvent) rEvent;

            updateLobbySlots();
        });
    }

    private void updateLobbySlots() {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    /*
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
        }, 5);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    @EventHandler
    public void onQuit(NetworkPlayerQuitEvent event) {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }

    @EventHandler
    public void onJoin(NetworkPlayerJoinEvent event) {
        lobbyUtil.updateSlots();
        lobbyUtil.getInventory().getViewers().forEach(humanEntity -> ((Player)humanEntity).updateInventory());
    }
    */
}
