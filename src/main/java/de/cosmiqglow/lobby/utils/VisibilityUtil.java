package de.cosmiqglow.lobby.utils;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class VisibilityUtil {

    public void changeVisibility(Plugin plugin, int value, Player player) {
        Preconditions.checkArgument(value >= 0, "The value can not be negative");
        switch (value) {
            case 0:
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (!player.canSee(online)) {
                        player.showPlayer(plugin, player);
                    }
                }
                break;
            case 1:
                //TODO: Schweine
                break;
            case 2:
                //TODO: Schweine
                for (Player online : Bukkit.getOnlinePlayers()) {
                    if (online == player) continue;
                    if (player.canSee(online)) {
                        player.hidePlayer(plugin, player);
                    }
                }
                break;
        }
        //Cloud.getInstance().getPlayer(player).setSetting(105,value);
    }
}