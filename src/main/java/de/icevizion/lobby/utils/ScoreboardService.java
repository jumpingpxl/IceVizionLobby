package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.cosmiqglow.component.friendsystem.spigot.FriendUpdateEvent;
import de.icevizion.scoreboard.Board;
import de.icevizion.scoreboard.BoardAPI;
import net.titan.cloudcore.player.ICloudPlayer;
import net.titan.protocol.utils.TimeUtilities;
import net.titan.spigot.Cloud;
import net.titan.spigot.event.NetworkPlayerJoinEvent;
import net.titan.spigot.event.NetworkPlayerQuitEvent;
import net.titan.spigot.event.PlayerCoinChangeEvent;
import net.titan.spigot.event.PlayerRankChangeEvent;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.ExecutorService;

/**
 * @author Patrick Zdarsky / Rxcki
 * @version 1.0
 * @since 14/12/2019 20:33
 */

public class ScoreboardService implements Listener {

    private final ExecutorService service;

    public ScoreboardService(ExecutorService service) {
        this.service = service;
    }

    public void createScoreboard(Player player) {
        Board board = BoardAPI.getInstance().getBoard(player);

        board.setDisplayName("§f§oI§fce§3V§fizion");

        board.setLine(10, "§4");
        board.setLine(9, "§7§lFreunde");

        board.setLine(7, "§3");
        board.setLine(6, "§7§lCoins");

        board.setLine(4, "§2");
        board.setLine(3, "§7§lOnlinezeit");

        board.setLine(1, "§1");

        service.execute(() -> {
            updateScoreboard(player);
        });
        board.show();
    }

    public void updateScoreboard(Player player) {
        Board board = BoardAPI.getInstance().getBoard(player);
        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);
        FriendProfile friendProfile = FriendSystem.getInstance().getFriendProfile(cloudPlayer);
        int onlineFriends = (int) friendProfile.getFriends().stream().filter(ICloudPlayer::isOnline).count();

        board.setLine(11, "§f§8» "+ cloudPlayer.getFullDisplayName());
        board.setLine(8, "§e§8» "+(onlineFriends == 0 ? "§c" : "§a")
                + onlineFriends + "§8/§b" + friendProfile.getRawFriends().size());
        board.setLine(5, "§2§8» §b" + cloudPlayer.getCoins());
        board.setLine(2, "§1§8» §b"+ TimeUtilities.getHours(cloudPlayer.getOnlineTime()) + " Stunden");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        createScoreboard(event.getPlayer());
        service.execute(() -> {
            for (Player player : Bukkit.getOnlinePlayers())
                updateScoreboard(player);
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        service.execute(() -> {
            for (Player player : Bukkit.getOnlinePlayers())
                if (player != event.getPlayer()) {
                    updateScoreboard(player);
                }
        });
    }

    @EventHandler
    public void onGlobalPlayerJoin(NetworkPlayerJoinEvent event) {
        service.execute(() -> {
            for (Player player : Bukkit.getOnlinePlayers())
                updateScoreboard(player);
        });
    }

    @EventHandler
    public void onGlobalPlayerQuit(NetworkPlayerQuitEvent event) {
        service.execute(() -> {
            for (Player player : Bukkit.getOnlinePlayers())
                updateScoreboard(player);
        });
    }

    @EventHandler
    public void onRankChange(PlayerRankChangeEvent event) {
        service.execute(() -> {
            updateScoreboard(event.getCloudPlayer().getPlayer());
        });
    }

    @EventHandler
    public void onCoinChange(PlayerCoinChangeEvent event) {
        service.execute(() -> {
            updateScoreboard(event.getCloudPlayer().getPlayer());
        });
    }

    @EventHandler
    public void onFriendUpdate(FriendUpdateEvent event) {
        if (event.getCloudPlayer().getPlayer() != null)
            updateScoreboard(event.getCloudPlayer().getPlayer());
        if (event.getFriendPlayer().getPlayer() != null)
            updateScoreboard(event.getFriendPlayer().getPlayer());
    }
}