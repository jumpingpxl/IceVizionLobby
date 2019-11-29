package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.lib.network.spigot.IClusterSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ConcurrentHashMap;

public class LobbyUtil {

    private final ConcurrentHashMap<IClusterSpigot, ItemStack> activeLobbys;
    private final Inventory inventory;

    public LobbyUtil() {
        this.inventory = Bukkit.createInventory(null, 27, "Waehle eine Lobby");
        this.activeLobbys = new ConcurrentHashMap<>();
    }

    public void addLobby(IClusterSpigot iClusterSpigot) {
        this.activeLobbys.putIfAbsent(iClusterSpigot,
                 new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName(iClusterSpigot.getDisplayName())
                .addLore(iClusterSpigot.getPlayerCount() + "/ " + iClusterSpigot.getPlayerLimit()).build());
    }

    public void removeLobby(IClusterSpigot iClusterSpigot) {
        this.activeLobbys.remove(iClusterSpigot);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
