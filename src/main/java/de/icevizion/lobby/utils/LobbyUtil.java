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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class LobbyUtil {

    private final Map<String, ItemStack> activeLobbies;
    private final ReentrantLock lock;
    private final Inventory inventory;

    public LobbyUtil() {
        this.inventory = Bukkit.createInventory(null, 27, "Waehle eine Lobby");
        this.inventory.setMaxStackSize(1);
        this.activeLobbies = new HashMap<>();
        this.lock = new ReentrantLock();
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

    /**
     * Updates the current lobbies when a lobby starts or shutdown
     */

    public void updateSlots() {
        lock.lock();
        try {
            List<IClusterSpigot> lobbies = Cloud.getInstance().getSpigots().stream()
                    .filter(clusterSpigot -> clusterSpigot.getServerType().equals("Lobby"))
                    .sorted(new SpigotComparator())
                    .collect(Collectors.toList());
            this.inventory.clear();
            this.activeLobbies.clear();
            lobbies.forEach(this::addLobby);
            this.inventory.getViewers().forEach(viewer-> {
                Player player = (Player) viewer;
                player.updateInventory();
            });
        } finally {
            lock.unlock();
        }
    }

    public void updateSlot(String serverName) {
        IClusterSpigot iClusterSpigot = Cloud.getInstance().getSpigot(serverName);
        if (!iClusterSpigot.getServerType().equals("Lobby"))
            return;

        lock.lock();
        try {
            if (activeLobbies.containsKey(serverName)) {

                new ItemBuilder(activeLobbies.get(serverName))
                        .addLore("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online").build();
            } else {
                addLobby(iClusterSpigot);
            }
            this.inventory.getViewers().forEach(viewer-> {
                Player player = (Player) viewer;
                player.updateInventory();
            });
        } finally {
            lock.unlock();
        }
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
