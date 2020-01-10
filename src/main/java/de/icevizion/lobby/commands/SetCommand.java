package de.icevizion.lobby.commands;

import de.icevizion.lobby.map.MapService;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCommand implements CommandExecutor {

    private final MapService mapService;

    public SetCommand(MapService mapService) {
        this.mapService = mapService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer((Player) sender);

            if (!cloudPlayer.hasPermission("lobby.location")) return false;

            if (args.length != 1) {
                cloudPlayer.sendMessage("§7Bitte benutze §c/location <spawn,oneline,guessit,kbffa,daily>");
            } else {
                if (args[0].isEmpty()) {
                    cloudPlayer.sendMessage("§cBitte gebe spawn,oneline,guessit,kbffa oder daily an");
                } else {
                    mapService.setValue(args[0].toLowerCase(), cloudPlayer.getPlayer().getLocation());
                    cloudPlayer.sendMessage("§7Du hast die Location §6" + args[0] + " §7gesetzt");
                }
            }
        }
        return true;
    }
}