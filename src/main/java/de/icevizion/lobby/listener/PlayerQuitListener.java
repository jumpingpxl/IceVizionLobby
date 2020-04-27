package de.icevizion.lobby.listener;

import net.titan.spigot.Cloud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        Cloud.getInstance().getPlayer(event.getPlayer()).extradataSet("location",
                event.getPlayer().getLocation().serialize());
    }
}