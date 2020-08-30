package de.icevizion.lobby.utils;

import de.cosmiqglow.component.friendsystem.spigot.FriendProfile;
import de.cosmiqglow.component.friendsystem.spigot.FriendSystem;
import de.icevizion.aves.scoreboard.ScoreboardBuilder;
import de.icevizion.lobby.LobbyPlugin;
import net.titan.cloudcore.player.ICloudPlayer;
import net.titan.cloudcore.player.rank.Rank;
import net.titan.protocol.utils.TimeUtilities;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.entity.Player;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class LobbyScoreboard {

	private final LobbyPlugin lobbyPlugin;
	private final FriendSystem friendSystem;

	public LobbyScoreboard(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
		//TODO -> Remove Singleton Pattern
		friendSystem = FriendSystem.getInstance();
	}

	public void createScoreboard(CloudPlayer cloudPlayer) {
		Player player = cloudPlayer.getPlayer();
		ScoreboardBuilder scoreboard = ScoreboardBuilder.create(lobbyPlugin.getLocales(), cloudPlayer);

		scoreboard.setDisplayName("scoreboardTitle");

		scoreboard.getLine(12).apply();
		updateRankTeam(cloudPlayer, scoreboard);
		scoreboard.getLine(10).apply();
		scoreboard.getLine(9).setValue("scoreboardFriendsTitle");

		scoreboard.getLine(7).apply();
		scoreboard.getLine(6).setValue("scoreboardCoinsTitle");

		scoreboard.getLine(4).apply();
		scoreboard.getLine(3).setValue("scoreboardOnlineTimeTitle");

		scoreboard.getLine(1).apply();

		scoreboard.setScoreboard();
	}

	public void resetScoreboard(CloudPlayer cloudPlayer) {
		ScoreboardBuilder scoreboard = ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer);
		scoreboard.delete();
	}

	public void updateRankTeam(CloudPlayer cloudPlayer, ScoreboardBuilder scoreboard) {
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin, () -> {
			Rank rank = cloudPlayer.getRank();
			scoreboard.getLine(11).setPrefix("scoreboardRankPrefix").setSuffix("scoreboardRankSuffix",
					rank.getColor(), rank.getName()).apply();
		});
	}

	public void updateRankTeam(CloudPlayer cloudPlayer) {
		updateRankTeam(cloudPlayer, ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer));
	}

	public void updateFriendsTeam(CloudPlayer cloudPlayer, ScoreboardBuilder scoreboard) {
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin, () -> {
			FriendProfile friendProfile = friendSystem.getFriendProfile(cloudPlayer);
			int onlineFriends = (int) friendProfile.getFriends()
					.stream()
					.filter(ICloudPlayer::isOnline)
					.count();
			scoreboard.getLine(8).setPrefix("scoreboardFriendsTeamPrefix",
					onlineFriends == 0 ? "§c0" : "§a" + onlineFriends).setSuffix(
					"scoreboardFriendsTeamSuffix", friendProfile.getRawFriends().size()).apply();
		});
	}

	public void updateFriendsTeam(CloudPlayer cloudPlayer) {
		updateFriendsTeam(cloudPlayer, ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer));
	}

	public void updateFriendsTeam(Player player) {
		CloudPlayer cloudPlayer = lobbyPlugin.getCloudService().getPlayer(player);
		updateFriendsTeam(cloudPlayer, ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer));
	}

	public void updateCoinsTeam(CloudPlayer cloudPlayer, ScoreboardBuilder scoreboard) {
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin,
				() -> scoreboard.getLine(5)
						.setPrefix("scoreboardCoinsTeamPrefix")
						.setSuffix("scoreboardCoinsTeamSuffix", cloudPlayer.getCoins())
						.apply());
	}

	public void updateCoinsTeam(CloudPlayer cloudPlayer) {
		updateCoinsTeam(cloudPlayer, ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer));
	}

	public void updateOnlineTimeTeam(CloudPlayer cloudPlayer, ScoreboardBuilder scoreboard) {
		lobbyPlugin.getServer().getScheduler().runTaskAsynchronously(lobbyPlugin,
				() -> scoreboard.getLine(2)
						.setPrefix("scoreboardOnlineTimeTeamPrefix",
								TimeUtilities.getHours(cloudPlayer.getOnlineTime()))
						.setSuffix("scoreboardOnlineTimeTeamSuffix"));
	}

	public void updateOnlineTimeTeam(CloudPlayer cloudPlayer) {
		updateOnlineTimeTeam(cloudPlayer, ScoreboardBuilder.of(lobbyPlugin.getLocales(), cloudPlayer));
	}
}
