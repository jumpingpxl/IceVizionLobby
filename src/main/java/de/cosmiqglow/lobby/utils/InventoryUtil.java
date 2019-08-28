package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.ItemBuilder;
import de.cosmiqglow.lobby.Lobby;
import net.titan.spigot.Cloud;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class InventoryUtil {

    private final ItemUtil itemUtil;
    private final Map<Player, Inventory> panelCache;
    private Inventory teleporter;

    public InventoryUtil(ItemUtil itemUtil) {
        this.itemUtil = itemUtil;
        this.panelCache = new HashMap<>();
        this.loadTeleporter();
    }

    public void loadTeleporter() {
        this.teleporter = Bukkit.createInventory(null, 27, "Minispiele");
        teleporter.setItem(10, new ItemBuilder(Material.GOLDEN_APPLE).setDisplayName("§eMiniUHC").build());
        teleporter.setItem(13, new ItemBuilder(Material.MAGMA_CREAM).setDisplayName("§aSpawn").build());
        teleporter.setItem(16, new ItemBuilder(Material.STICK).setDisplayName("§bKnockbackFFA").build());
    }

    private Inventory createPanelInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, "Einstellungen");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getSettingsLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        CloudPlayer cloudPlayer = Cloud.getInstance().getPlayer(player);

        int privatMessage = cloudPlayer.getSetting(Lobby.PRIVAT_MESSAGE);
        int party = cloudPlayer.getSetting(Lobby.PARTY);
        int friend = cloudPlayer.getSetting(Lobby.FRIENDS);
        int jump = cloudPlayer.getSetting(Lobby.JUMP);

        setState(inventory, 0, privatMessage);
        setState(inventory, 18, party);
        setState(inventory, 27, friend);
        setState(inventory, 36, jump);

      /*  inventory.setItem(3, privatMessage == 0 ? new ItemBuilder(Material.LIME_DYE)
                .setDisplayName(inventory.getItem(3).getItemMeta().getDisplayName()).build() : inventory.getItem(3));
        inventory.setItem(4, privatMessage == 1 ? new ItemBuilder(Material.ORANGE_DYE)
                .setDisplayName(inventory.getItem(4).getItemMeta().getDisplayName()).build() : inventory.getItem(4));
        inventory.setItem(5, privatMessage == 2 ? new ItemBuilder(Material.ROSE_RED)
                .setDisplayName(inventory.getItem(5).getItemMeta().getDisplayName()).build() : inventory.getItem(5));

        inventory.setItem(12, party == 0 ? new ItemBuilder(Material.LIME_DYE)
                .setDisplayName(inventory.getItem(12).getItemMeta().getDisplayName()).build() : inventory.getItem(12));
        inventory.setItem(13, party == 1 ? new ItemBuilder(Material.ORANGE_DYE)
                .setDisplayName(inventory.getItem(13).getItemMeta().getDisplayName()).build() : inventory.getItem(13));
        inventory.setItem(14, party == 2 ? new ItemBuilder(Material.ROSE_RED)
                .setDisplayName(inventory.getItem(14).getItemMeta().getDisplayName()).build() : inventory.getItem(14));

        inventory.setItem(21, friend == 0 ? new ItemBuilder(Material.LIME_DYE)
                .setDisplayName(inventory.getItem(21).getItemMeta().getDisplayName()).build() : inventory.getItem(21));
        inventory.setItem(22, friend == 1 ? new ItemBuilder(Material.ORANGE_DYE)
                .setDisplayName(inventory.getItem(22).getItemMeta().getDisplayName()).build() : inventory.getItem(22));
        inventory.setItem(23, friend == 2 ? new ItemBuilder(Material.ROSE_RED)
                .setDisplayName(inventory.getItem(23).getItemMeta().getDisplayName()).build() : inventory.getItem(23));

        inventory.setItem(30, jump == 0 ? new ItemBuilder(Material.LIME_DYE)
                .setDisplayName(inventory.getItem(30).getItemMeta().getDisplayName()).build() : inventory.getItem(30));
        inventory.setItem(31, jump == 1 ? new ItemBuilder(Material.ORANGE_DYE)
                .setDisplayName(inventory.getItem(31).getItemMeta().getDisplayName()).build() : inventory.getItem(31));
        inventory.setItem(32, jump == 2 ? new ItemBuilder(Material.ROSE_RED)
                .setDisplayName(inventory.getItem(32).getItemMeta().getDisplayName()).build() : inventory.getItem(32));*/
        return inventory;
    }

    private void setState(Inventory inv, int kat , int val) {
        ItemStack state;
        switch (val) {
            case 0:
                state = new ItemBuilder(Material.LIME_DYE).setDisplayName("Für alle").build();
                inv.setItem(kat + ( 3 + val), state);
                break;
            case 1:
                state = new ItemBuilder(Material.ORANGE_DYE).setDisplayName("Für alle").build();
                inv.setItem(kat + ( 3 + val), state);
                break;
            case 2:
                state = new ItemBuilder(Material.ROSE_RED).setDisplayName("Für alle").build();
                inv.setItem(kat + ( 3 + val), state);
                break;
        }
    }

    public Inventory getPanel(Player player) {
        return getPanelCache().containsKey(player) ? getPanelCache().get(player) : createPanelInventory(player);
    }

    public Inventory getTeleporter() {
        return teleporter;
    }

    public Map<Player, Inventory> getPanelCache() {
        return panelCache;
    }
}
