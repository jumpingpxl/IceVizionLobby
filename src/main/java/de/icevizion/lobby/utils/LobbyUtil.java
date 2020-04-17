package de.icevizion.lobby.utils;

import de.icevizion.aves.item.ItemBuilder;
import net.titan.lib.network.spigot.IClusterSpigot;
import net.titan.lib.utils.SpigotComparator;
import net.titan.spigot.Cloud;
import net.titan.spigot.network.spigot.ClusterSpigot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class LobbyUtil {

    private final ConcurrentHashMap<String, ItemStack> activeLobbies;
    private final Inventory inventory;

    public LobbyUtil() {
        this.inventory = Bukkit.createInventory(null, 27, "Waehle eine Lobby");
        this.inventory.setMaxStackSize(1);
        this.activeLobbies = new ConcurrentHashMap<>();
        this.loadLobbies();
    }

    /**
     * Loads all current active lobby server into the underlying map
     */

    private void loadLobbies() {
        for (ClusterSpigot spigot : Cloud.getInstance().getSpigots()) {
            if (spigot.getDisplayName().startsWith("Lobby")) {
                addLobby(spigot);
            }
        }
    }

    /**
     * Add a specific spigot from the service
     * @param iClusterSpigot to add
     */

    private void addLobby(IClusterSpigot iClusterSpigot) {
        ItemStack server =  new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online").build();
        this.activeLobbies.putIfAbsent(iClusterSpigot.getUuid(), server);
        this.inventory.addItem(server);
    }

    public void updateSlots() {
        List<IClusterSpigot> lobbies = Cloud.getInstance().getSpigots().stream()
                .filter(clusterSpigot -> clusterSpigot.getDisplayName().startsWith("Lobby"))
                .sorted(new SpigotComparator())
                .collect(Collectors.toList());

       // Bukkit.getLogger().info("Lobby Update!");
       // lobbies.forEach((spigot)-> Bukkit.getLogger().info(spigot.getDisplayName()+" -> " + spigot.getPlayerCount()));
        this.inventory.clear();
        this.activeLobbies.clear();
       // lobbies.forEach(this::removeLobby);
        lobbies.forEach(this::addLobby);
        this.inventory.getViewers().forEach(viewer-> {
            Player player = (Player) viewer;
            player.updateInventory();
        });
    }

    /**
     * Removes a specific spigot from the service
     * @param iClusterSpigot to remove
     */

    private void removeLobby(IClusterSpigot iClusterSpigot) {
        ItemStack stack = this.activeLobbies.remove(iClusterSpigot.getUuid());
        this.inventory.remove(stack);
        this.inventory.getViewers().forEach(viewer-> {
            Player player = (Player) viewer;
            player.updateInventory();
        });
    }

    /**
     * Getter for the current size of the underlying list.
     * @return The current amount of registered lobbies
     */

    public int getCurrentSize() {
        return activeLobbies.size();
    }

    /**
     * Returns the inventory for the selector
     * @return the inventory
     */

    public Inventory getInventory() {
        return inventory;
    }
}
