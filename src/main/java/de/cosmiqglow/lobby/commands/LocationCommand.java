package de.cosmiqglow.lobby.commands;

import de.cosmiqglow.lobby.Lobby;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand implements CommandExecutor {

    private final Lobby plugin;

    public LocationCommand(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Du bist kein Spieler");
            return true;
        } else {
            Player player = (Player) sender;
            Location location = player.getLocation();


            return true;
        }
    }
}