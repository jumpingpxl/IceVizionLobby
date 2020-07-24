package de.icevizion.lobby.listener;

import de.icevizion.lobby.utils.LobbyUtil;
import net.titan.lib.redisevent.events.PlayerJoinEvent;
import net.titan.lib.redisevent.events.PlayerQuitEvent;
import net.titan.lib.redisevent.events.PlayerServerSwitchEvent;
import net.titan.lib.redisevent.events.ServerAvailableEvent;
import net.titan.lib.redisevent.events.ServerUnavailableEvent;
import net.titan.spigot.Cloud;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class NetworkListener implements Listener {

    private final Plugin plugin;
    private final LobbyUtil lobbyUtil;

    public NetworkListener(Plugin plugin, LobbyUtil lobbyUtil) {
        this.plugin = plugin;
        this.lobbyUtil = lobbyUtil;
        initRedisEvents();
    }

    private void initRedisEvents() {
        Cloud.getInstance().getRedisEventManager().registerListener(PlayerJoinEvent.class, rEvent -> {
            PlayerJoinEvent joinEvent = (PlayerJoinEvent) rEvent;
            if (joinEvent.getServer().startsWith("LOBBY")) {
                lobbyUtil.updateSlot(joinEvent.getServer());
            }
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerQuitEvent.class, rEvent -> {
            PlayerQuitEvent quitEvent = (PlayerQuitEvent) rEvent;

            if (quitEvent.getServer().startsWith("LOBBY")) {
                lobbyUtil.updateSlot(quitEvent.getServer());
            }
        });

        Cloud.getInstance().getRedisEventManager().registerListener(PlayerServerSwitchEvent.class, rEvent -> {
            PlayerServerSwitchEvent switchEvent = (PlayerServerSwitchEvent) rEvent;
            //Delay this update because the event is too fast and sometimes redis has not the right data yet
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                lobbyUtil.updateSlot(switchEvent.getFrom());
                lobbyUtil.updateSlot(switchEvent.getTo());
            }, 5);
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerAvailableEvent.class, rEvent -> {
            ServerAvailableEvent event = (ServerAvailableEvent) rEvent;
            if (event.getServer().startsWith("LOBBY")) {
                lobbyUtil.updateSlots();
            }
        });

        Cloud.getInstance().getRedisEventManager().registerListener(ServerUnavailableEvent.class, rEvent -> {
            ServerUnavailableEvent event = (ServerUnavailableEvent) rEvent;
            System.out.println(event.getServer());
            if (event.getServer().startsWith("LOBBY")) {
                lobbyUtil.updateSlots();
            }
        });
    }
}
