package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.spigot.Cloud;
import net.titan.spigot.network.spigot.ClusterSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

public class LobbyUtil {

    private final ConcurrentHashMap<String, ItemStack> activeLobbys;
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
                System.out.println(spigot.getID());
                addLobby(spigot);
            }
        }
        System.out.println("[Lobby] Found " + activeLobbys.size() + " current active lobbys");
    }

    public void addLobby(IClusterSpigot iClusterSpigot) {
        ItemStack server =  new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online").build();
        this.activeLobbys.putIfAbsent(iClusterSpigot.getUuid(), server);
        this.inventory.addItem(server);
    }

    public void updateLobby(IClusterSpigot iClusterSpigot) {
        ItemStack itemStack = this.activeLobbys.get(iClusterSpigot.getUuid());
        ItemStack server =  new ItemBuilder(itemStack)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore(Collections.singletonList("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online"))
                .build();
        this.inventory.remove(itemStack);
        this.inventory.addItem(server);
        this.activeLobbys.replace(iClusterSpigot.getUuid(),server);
    }


    public void removeLobby(IClusterSpigot iClusterSpigot) {
        ItemStack stack = this.activeLobbys.remove(iClusterSpigot);
        this.inventory.remove(stack);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
