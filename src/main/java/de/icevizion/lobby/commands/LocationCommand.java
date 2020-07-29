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
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		if (!(commandSender instanceof Player)) {
			return true;
		}

		Player player = (Player) commandSender;
		CloudPlayer cloudPlayer = lobbyPlugin.getCloud().getPlayer(player);
		if (cloudPlayer.hasPermission("lobby.location")) {
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "noPermission");
			return true;
		}

		if (args.length != 1) {
			lobbyPlugin.getLocales().sendMessage(cloudPlayer, "locationArgs");
			return true;
		}

		lobbyPlugin.getMapService().setValue(args[0].toLowerCase(), player.getLocation());
		lobbyPlugin.getLocales().sendMessage(cloudPlayer, "locationSet", args[0].toUpperCase());
		return true;
	}
}