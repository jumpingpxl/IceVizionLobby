package de.icevizion.lobby.listener;

import de.icevizion.component.spectatorsystem.spigot.event.SpectateEndEvent;
import de.icevizion.lobby.utils.ItemUtil;
import net.titan.spigot.Cloud;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerSpectatorListener implements Listener {

    private final ItemUtil itemUtil;

    public PlayerSpectatorListener(ItemUtil itemUtil) {
        this.itemUtil = itemUtil;
    }

    @EventHandler
    public void onEnd(SpectateEndEvent event) {
        itemUtil.setItems(Cloud.getInstance().getPlayer(event.getPlayer()));
    }
}
