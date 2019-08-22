package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.ItemBuilder;
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

    private Inventory createPanelInventory() {
        Inventory inventory = Bukkit.createInventory(null, 54, "Einstellungen");
        for (Map.Entry<Integer, ItemStack> entry : itemUtil.getSettingsLayout().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
        return inventory;
    }

    public Inventory getPanel(Player player) {
        return getPanelCache().containsKey(player) ? getPanelCache().get(player) : createPanelInventory();
    }

    public Inventory getTeleporter() {
        return teleporter;
    }

    public Map<Player, Inventory> getPanelCache() {
        return panelCache;
    }
}
