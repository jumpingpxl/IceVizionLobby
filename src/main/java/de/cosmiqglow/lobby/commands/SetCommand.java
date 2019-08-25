package de.cosmiqglow.lobby.commands;

import de.cosmiqglow.lobby.map.MapService;
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
        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player player = (Player) sender;
            if (args.length != 1) {
                player.sendMessage("§7Bitte benutze §c/set <spawn,uhc,kbffa,daily>");
                return  true;
            } else {
                if (args[0].isEmpty()) {
                    player.sendMessage("§cBitte gebe spawn,uhc,kbffa oder daily an");
                    return true;
                } else {
                    String name = args[0];
                    Location location = player.getLocation();
                    mapService.setValue(name, location);
                    player.sendMessage("§7Du hast die Location §6" + name + " §7gesetzt");
                    return true;
                }
            }
        }
    }
}