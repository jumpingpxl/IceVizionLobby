package de.icevizion.lobby.commands;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand implements CommandExecutor {

	private final LobbyPlugin lobbyPlugin;

	public LocationCommand(LobbyPlugin lobbyPlugin) {
		this.lobbyPlugin = lobbyPlugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;
		CloudPlayer cloudPlayer = lobbyPlugin.getCloudService().getPlayer(player);
		if (cloudPlayer.hasPermission("lobby.location")) {
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "noPermission");
			return true;
		}

		if (args.length != 1) {
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "locationArgs");
			return true;
		}

		lobbyPlugin.getLocationProvider().addLocation(args[0].toLowerCase(), player.getLocation());
		lobbyPlugin.getLocales().sendMessage(cloudPlayer, "locationSet", args[0].toUpperCase());
		return true;
	}
}