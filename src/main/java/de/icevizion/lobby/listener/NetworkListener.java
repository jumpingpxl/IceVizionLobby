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
            lobbyUtil.updateSlot(joinEvent.getServer());
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerQuitEvent.class, rEvent -> {
            PlayerQuitEvent quitEvent = (PlayerQuitEvent) rEvent;
            lobbyUtil.updateSlot(quitEvent.getServer());
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerServerSwitchEvent.class, rEvent -> {
            PlayerServerSwitchEvent switchEvent = (PlayerServerSwitchEvent) rEvent;
            //Delay this update because the event is too fast and sometimes redis has not the right data yet
            Bukkit.getScheduler().runTaskLater(lobby, () -> {
                lobbyUtil.updateSlot(switchEvent.getFrom());
                lobbyUtil.updateSlot(switchEvent.getTo());
            }, 5);
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerAvailableEvent.class, rEvent -> {
            lobbyUtil.updateSlots();
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerUnavailableEvent.class, rEvent -> {
            ServerUnavailableEvent event = (ServerUnavailableEvent) rEvent;
            if (event.getServer().equalsIgnoreCase("Lobby")) {
                lobbyUtil.updateSlots();
            }
        });
    }
}
