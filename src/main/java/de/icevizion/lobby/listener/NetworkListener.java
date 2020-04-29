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

            System.out.println("Player joined on Server " + joinEvent.getServer());

            lobbyUtil.updateSlot(joinEvent.getServer());
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerQuitEvent.class, rEvent -> {
            PlayerQuitEvent quitEvent = (PlayerQuitEvent) rEvent;

            System.out.println("Player left on Server " + quitEvent.getServer());

            lobbyUtil.updateSlot(quitEvent.getServer());
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerServerSwitchEvent.class, rEvent -> {
            PlayerServerSwitchEvent switchEvent = (PlayerServerSwitchEvent) rEvent;

            System.out.println("Player switched from " + switchEvent.getFrom());
            System.out.println("Player switched to " + switchEvent.getTo());

            //Delay this update because the event is too fast and sometimes redis has not the right data yet
            Bukkit.getScheduler().runTaskLater(lobby, () -> {
                lobbyUtil.updateSlot(switchEvent.getFrom());
            }, 5);
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerAvailableEvent.class, rEvent -> {
            ServerAvailableEvent availableEvent = (ServerAvailableEvent) rEvent;

            lobbyUtil.updateSlots();
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerUnavailableEvent.class, rEvent -> {
            ServerUnavailableEvent unavailableEvent = (ServerUnavailableEvent) rEvent;

            System.out.println("Lobby " + unavailableEvent.getServer() + " goes away");

            lobbyUtil.updateSlots();
        });
    }
}
