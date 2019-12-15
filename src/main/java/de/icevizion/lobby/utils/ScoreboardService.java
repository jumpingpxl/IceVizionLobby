package de.icevizion.lobby.utils;

import de.icevizion.scoreboard.Board;
import de.icevizion.scoreboard.BoardAPI;
import net.titan.protocol.utils.TimeUtilities;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 14/12/2019 20:33
 */

public class ScoreboardService implements Listener {

    public void createScoreboard(Player player) {
        Board board = BoardAPI.getInstance().getBoard(player);
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        board.setDisplayName("§f§oI§3V §8•"+cloudPlayer.getFullDisplayName());

        board.setLine(6, "§7§lCoins");

        board.setLine(4, "");
        board.setLine(3, "§7§lOnlinezeit");

        board.setLine(1, "§1");

        updateScoreboard(player);
        board.show();
    }

    public void updateScoreboard(Player player) {
        Board board = BoardAPI.getInstance().getBoard(player);
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        board.setDisplayName("§f§oI§3V §8•"+cloudPlayer.getFullDisplayName());
        board.setLine(5, "§2§3" + cloudPlayer.getCoins());
        board.setLine(2, "§1§3"+ TimeUtilities.getHours(cloudPlayer.getOnlineTime())+" Stunden");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        createScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onRankChange(PlayerRankChangeEvent event) {
        updateScoreboard(event.getCloudPlayer().getPlayer());
    }
}
