package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.spigot.Cloud;
import net.titan.spigot.network.spigot.ClusterSpigot;
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
        this.loadLobbys();
    }

    private void loadLobbys() {
        System.out.println("[Lobby] Searching for lobby's");
        for (ClusterSpigot spigot : Cloud.getInstance().getSpigots()) {
            if (spigot.getDisplayName().startsWith("Lobby")) {
                addLobby(spigot);
            }
        }
        System.out.println("[Lobby] Found " + activeLobbys.size() + " current active lobbys");
        for (ItemStack value : activeLobbys.values()) {
            inventory.addItem(value);
        }
    }

    public void addLobby(IClusterSpigot iClusterSpigot) {
        ItemStack server =  new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online").build();
        this.activeLobbys.putIfAbsent(iClusterSpigot, server);
        this.inventory.addItem(server);
    }

    public void removeLobby(IClusterSpigot iClusterSpigot) {
        ItemStack stack = this.activeLobbys.remove(iClusterSpigot);
        this.inventory.remove(stack);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
