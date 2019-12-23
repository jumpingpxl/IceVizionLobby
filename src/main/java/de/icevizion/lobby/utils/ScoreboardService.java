package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.Lobby;
import de.icevizion.scoreboard.Board;
import de.icevizion.scoreboard.BoardAPI;
import net.titan.cloudcore.player.ICloudPlayer;
import net.titan.protocol.utils.TimeUtilities;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
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

    private final Lobby lobby;

    public ScoreboardService(Lobby lobby) {
        this.lobby = lobby;
    }

    public void createScoreboard(final Player player) {
        Board board = BoardAPI.getInstance().getBoard(player);

        board.setDisplayName("§f§oI§fce§3V§fizion");

        board.setLine(10, "§4");
        board.setLine(9, "§7§lFreunde");

        board.setLine(7, "§3");
        board.setLine(6, "§7§lCoins");

        board.setLine(4, "§2");
        board.setLine(3, "§7§lOnlinezeit");

        board.setLine(1, "§1");

        updateScoreboard(player);
        board.show();
    }

    public void updateScoreboard(final Player player) {
        Bukkit.getScheduler().runTaskAsynchronously(lobby, () -> {
            Board board = BoardAPI.getInstance().getBoard(player);
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
            FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
            int onlineFriends = (int) friendProfile.getFriends().stream().filter(ICloudPlayer::isOnline).count();

            board.setLine(11, "§f§8» "+cloudPlayer.getFullDisplayName());
            board.setLine(8, "§e§8» "+(onlineFriends == 0 ? "§c" : "§a")
                    + onlineFriends + "§8/§6" + friendProfile.getRawFriends().size());
            board.setLine(5, "§2§8» §6" + cloudPlayer.getCoins());
            board.setLine(2, "§1§8» §6"+ TimeUtilities.getHours(cloudPlayer.getOnlineTime()) + " Stunden");
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        createScoreboard(event.getPlayer());
    }

    @EventHandler
    public void onGlobalPlayerJoin(NetworkPlayerJoinEvent event) {
        for (Player player : Bukkit.getOnlinePlayers())
            updateScoreboard(player);
    }

    @EventHandler
    public void onGlobalPlayerQuit(NetworkPlayerQuitEvent event) {
        for (Player player : Bukkit.getOnlinePlayers())
            updateScoreboard(player);
    }

    @EventHandler
    public void onRankChange(PlayerRankChangeEvent event) {
        updateScoreboard(event.getCloudPlayer().getPlayer());
    }
}
