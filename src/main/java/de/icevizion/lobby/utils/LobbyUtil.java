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

    private int currentSlot;

    public LobbyUtil() {
        this.currentSlot = 11;
        this.inventory = Bukkit.createInventory(null, 27, "Waehle eine Lobby");
        this.inventory.setMaxStackSize(1);
        this.activeLobbies = new HashMap<>();
        this.lock = new ReentrantLock();
        this.loadLayout();
        this.loadLobbies();

    }

    private void loadLayout() {
        inventory.setItem(0, ItemUtil.PANE);
        inventory.setItem(8, ItemUtil.PANE);
        inventory.setItem(9, ItemUtil.PANE);
        inventory.setItem(17, ItemUtil.PANE);
        inventory.setItem(18, ItemUtil.PANE);
        inventory.setItem(26, ItemUtil.PANE);
    }

    /**
     * Loads all current active lobby server into the underlying map
     */

    private void loadLobbies() {
        for (ClusterSpigot spigot : Cloud.getInstance().getSpigots()) {
            if (spigot.getServerType().equals("Lobby")) {
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
        activeLobbies.putIfAbsent(iClusterSpigot.getUuid(), server);
        inventory.setItem(currentSlot, server);
        currentSlot++;
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
            clearLobbySlots();
            currentSlot = 11;
            activeLobbies.clear();
            lobbies.forEach(this::addLobby);
            inventory.getViewers().forEach(viewer-> {
                Player player = (Player) viewer;
                player.updateInventory();
            });
        } finally {
            lock.unlock();
        }
    }

    private void clearLobbySlots() {
        for (int i = 11; i <= 15; i++) {
            inventory.remove(inventory.getItem(i));
        }
    }

    public void updateSlot(String serverName) {
        IClusterSpigot iClusterSpigot = Cloud.getInstance().getSpigot(serverName);
        if (iClusterSpigot == null || !iClusterSpigot.getServerType().equals("Lobby"))
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
