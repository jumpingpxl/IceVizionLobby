package de.icevizion.lobby.commands;

import de.icevizion.lobby.Lobby;
import de.icevizion.lobby.utils.SettingsUtil;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand implements CommandExecutor {

    private final Lobby plugin;

    public EventCommand(Lobby plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer((Player) sender);
            Bukkit.broadcastMessage("test");
            Bukkit.broadcastMessage("" + cloudPlayer.getSetting(SettingsUtil.EVENT));
            switch (cloudPlayer.getSetting(SettingsUtil.EVENT)) {
                case 1:
                    Bukkit.broadcastMessage("test1");
                    cloudPlayer.setSetting(SettingsUtil.EVENT, 0);
                    plugin.getSnowService().removePlayer(cloudPlayer.getPlayer());
                    cloudPlayer.sendMessage(plugin.getPrefix() + "§7Du hast den Schnee §cdeaktiviert");
                    break;
                case 0:
                    Bukkit.broadcastMessage("test2");
                    cloudPlayer.setSetting(SettingsUtil.EVENT, 1);
                    plugin.getSnowService().addPlayer(cloudPlayer.getPlayer());
                    cloudPlayer.sendMessage(plugin.getPrefix() + "§7Du hast den Schnee §aaktiviert");
                    break;
            }
        }
        return true;
    }
}
