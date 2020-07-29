package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.lobby.LobbyPlugin;
import de.icevizion.scoreboard.Board;
import de.icevizion.scoreboard.BoardAPI;
import net.titan.cloudcore.player.ICloudPlayer;
import net.titan.protocol.utils.TimeUtilities;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.Objects;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class Scoreboard {

	private final LobbyPlugin lobbyPlugin;
	private final BoardAPI boardApi;
	private final FriendSystem friendSystem;

	public Scoreboard(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		//TODO -> Remove Singleton Pattern
		boardApi = BoardAPI.getInstance();
		//TODO -> Remove Singleton Pattern
		friendSystem = FriendSystem.getInstance();
	}

	public void createScoreboard(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		Board board = boardApi.getBoard(player);
		board.setDisplayName(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardTitle"));
		board.setLine(12, "§1§1");

		board.setLine(10, "§1§2");
		board.setLine(9, lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardFriendsTitle"));

		board.setLine(7, "§1§3");
		board.setLine(6, lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardCoinsTitle"));

		board.setLine(4, "§1§4");
		board.setLine(3, lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardOnlineTimeTitle"));

		board.setLine(1, "§1§5");

		updateScoreboard(cloudPlayer, board);
	}

	public void resetScoreboard(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		Board board = boardApi.getBoard(player);
		board.removeAllTeams();
		board.reset();
	}

	public void updatePlayerNameTeam(CloudPlayer cloudPlayer, Board board) {
		Team playerName = getTeam(board, 11, "playerName", "§2§1");
		playerName.setPrefix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardNameTeamPrefix",
				cloudPlayer.getRank().getColor()));
		playerName.setSuffix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardNameTeamSuffix",
				cloudPlayer.getDisplayName()));
	}

	public void updatePlayerNameTeam(CloudPlayer cloudPlayer) {
		updatePlayerNameTeam(cloudPlayer, boardApi.getBoard(cloudPlayer.getPlayer()));
	}

	public void updateFriendsTeam(CloudPlayer cloudPlayer, Board board) {
		FriendProfile friendProfile = friendSystem.getFriendProfile(cloudPlayer);
		int onlineFriends = (int) friendProfile.getFriends().stream().filter(ICloudPlayer::isOnline).count();
		Team friends = getTeam(board, 8, "friends", "§2§2");
		friends.setPrefix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardFriendsTeamPrefix",
				onlineFriends == 0 ? "§c0" : "§a" + onlineFriends));
		friends.setSuffix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardFriendsTeamSuffix",
				friendProfile.getRawFriends().size()));
	}

	public void updateFriendsTeam(CloudPlayer cloudPlayer) {
		updateFriendsTeam(cloudPlayer, boardApi.getBoard(cloudPlayer.getPlayer()));
	}

	public void updateFriendsTeam(Player player) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);
		updateFriendsTeam(cloudPlayer, boardApi.getBoard(cloudPlayer.getPlayer()));
	}

	public void updateCoinsTeam(CloudPlayer cloudPlayer, Board board) {
		Team coins = getTeam(board, 5, "coins", "§2§3");
		coins.setPrefix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardCoinsTeamPrefix"));
		coins.setSuffix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardCoinsTeamSuffix",
				cloudPlayer.getCoins()));
	}

	public void updateCoinsTeam(CloudPlayer cloudPlayer) {
		updateCoinsTeam(cloudPlayer, boardApi.getBoard(cloudPlayer.getPlayer()));
	}

	public void updateOnlineTimeTeam(CloudPlayer cloudPlayer, Board board) {
		Team onlineTime = getTeam(board, 5, "onlineTime", "§2§3");
		//TODO -> Remove Singleton Pattern
		onlineTime.setPrefix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardOnlineTimeTeamPrefix",
				TimeUtilities.getHours(cloudPlayer.getOnlineTime())));
		onlineTime.setSuffix(lobbyPlugin.getLocales().getString(cloudPlayer, "scoreboardOnlineTimeTeamSuffix"));
	}

	public void updateOnlineTimeTeam(CloudPlayer cloudPlayer) {
		updateOnlineTimeTeam(cloudPlayer, boardApi.getBoard(cloudPlayer.getPlayer()));
	}

	public void updateScoreboard(CloudPlayer cloudPlayer, Board board) {
		updatePlayerNameTeam(cloudPlayer, board);
		updateFriendsTeam(cloudPlayer, board);
		updateCoinsTeam(cloudPlayer, board);
		updateOnlineTimeTeam(cloudPlayer, board);
	}

	private Team getTeam(Board board, int line, String teamName, String defaultEntry) {
		Team team = board.getTeam(teamName);
		if (Objects.isNull(team)) {
			team = board.getScoreboard().registerNewTeam(teamName);
			team.addEntry(defaultEntry);
			board.setScore(defaultEntry, line);
		}

		return team;
	}
}
