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

public class LobbyUtil implements Runnable {

    private final ConcurrentHashMap<String, ItemStack> activeLobbys;
    private final Inventory inventory;

    public LobbyUtil() {
        this.inventory = Bukkit.createInventory(null, 27, "Waehle eine Lobby");
        this.inventory.setMaxStackSize(1);
        this.activeLobbys = new ConcurrentHashMap<>();
        this.loadLobbies();
    }

    @Override
    public void run() {
        this.updateSlots();
    }

    private void loadLobbies() {
        System.out.println("[Lobby] Searching for lobby's");
        for (ClusterSpigot spigot : Cloud.getInstance().getSpigots()) {
            if (spigot.getDisplayName().startsWith("Lobby")) {
                addLobby(spigot);
            }
        }
        System.out.println("[Lobby] Found " + activeLobbys.size() + " current active lobbys");
    }

    private void addLobby(IClusterSpigot iClusterSpigot) {
        ItemStack server =  new ItemBuilder(Material.GLOWSTONE_DUST)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online").build();
        this.activeLobbys.putIfAbsent(iClusterSpigot.getUuid(), server);
        this.inventory.addItem(server);
    }

    /*
    public void updateLobby(IClusterSpigot iClusterSpigot) {
        ItemStack itemStack = this.activeLobbys.get(iClusterSpigot.getUuid());
        ItemStack server =  new ItemBuilder(itemStack)
                .setDisplayName("§6" + iClusterSpigot.getDisplayName())
                .addLore(Collections.singletonList("§a" + iClusterSpigot.getPlayerCount() + " §fSpieler online"))
                .build();
        this.inventory.remove(itemStack);
        this.inventory.addItem(server);
        this.activeLobbys.replace(iClusterSpigot.getUuid(),server);
    }*/

    public void updateSlots() {
        List<IClusterSpigot> lobbies = Cloud.getInstance().getSpigots().stream()
                .filter(clusterSpigot -> clusterSpigot.getDisplayName().startsWith("Lobby"))
                .sorted(new SpigotComparator())
                .collect(Collectors.toList());

        Bukkit.getLogger().info("Lobby Update!");
        lobbies.forEach((spigot)-> Bukkit.getLogger().info(spigot.getDisplayName()+" -> "+spigot.getPlayerCount()));
        lobbies.forEach(this::removeLobby);
        lobbies.forEach(this::addLobby);
        this.inventory.getViewers().forEach(viewer-> {
            Player player = (Player) viewer;
            player.updateInventory();
        });
    }


    private void removeLobby(IClusterSpigot iClusterSpigot) {
        ItemStack stack = this.activeLobbys.remove(iClusterSpigot.getUuid());
        this.inventory.remove(stack);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
