package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemUtil {

    private final ItemStack teleporter, panel, tnt, porkchop, slime;
    private final Map<Integer, ItemStack> settingsLayout;

    public ItemUtil() {
        this.teleporter = new ItemBuilder(Material.PRISMARINE_SHARD).setDisplayName("§e✦ §bMinispiele").
                addLore("§e» §7Lässt dich sofort mit dem", "§7Spiele-Server verbinden.").build();
        this.panel = new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§e✦ Einstellungen").
                addLore("§e» §7Passe dein Spielerlebnis an.").build();
        this.tnt = new ItemBuilder(Material.TNT).setDisplayName("§e✦ §cBombe")
                .addLore("§e» §7Sprenge alle Spieler", "§7in die Luft.").build();
        this.porkchop = new ItemBuilder(Material.PORKCHOP).setDisplayName("§e✦ §dSpielerschweinchen")
                .addLore("§e» §7Verwandle alle Spieler ","§7in §dSchweine§7.").build();
        this.slime = new ItemBuilder(Material.SLIME_BALL).setDisplayName("§e✦ §aLade Spieler")
                .addLore("§e» §7Lade wieder alle Spieler§7.").build();
        this.settingsLayout = loadLayout();
    }

    private HashMap<Integer, ItemStack> loadLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(0, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("Privatnachrichten").build());
        layout.put(1, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(3, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(4, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(5, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(9, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("Party").build());
        layout.put(10, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(12, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(13, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(14, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(18, new ItemBuilder(Material.GOLDEN_HELMET).setDisplayName("Freunde").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        layout.put(19, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(21, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(22, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(23, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(27, new ItemBuilder(Material.SLIME_BALL).setDisplayName("Nachspringen").build());
        layout.put(28, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(30, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(31, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(32, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(36, new ItemBuilder(Material.PORKCHOP).setDisplayName("Spieler in Schweine verwandeln").build());
        layout.put(37, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(39, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(40, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(41, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(53, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cSchließen").build());
        return layout;
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(1, teleporter);
        player.getInventory().setItem(4, tnt);
        player.getInventory().setItem(7, panel);
    }

    public Map<Integer, ItemStack> getSettingsLayout() {
        return settingsLayout;
    }

    public ItemStack getPorkchop() {
        return porkchop;
    }

    public ItemStack getSlime() {
        return slime;
    }

    public ItemStack getTNT() {
        return tnt;
    }
}