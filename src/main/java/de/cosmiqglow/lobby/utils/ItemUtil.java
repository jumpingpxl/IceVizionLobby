package de.cosmiqglow.lobby.utils;

import de.cosmiqglow.aves.item.CustomPlayerHeadBuilder;
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
    private final Map<Integer, ItemStack> friendLayout;

    public ItemUtil() {
        this.teleporter = new ItemBuilder(Material.PRISMARINE_SHARD).setDisplayName("§e✦ §bMinispiele").
                addLore("§e» §7Lässt dich sofort mit dem", "§7Spiele-Server verbinden.").build();
        this.panel = new ItemBuilder(Material.NOTE_BLOCK).setDisplayName("§e✦ Einstellungen").
                addLore("§e» §7Passe dein Spielerlebnis an.").build();
        this.tnt = new ItemBuilder(Material.TNT).setDisplayName("§e✦ §cBombe")
                .addLore("§e» §7Sprenge alle Spieler", "§7in die Luft.").build();
        this.porkchop = new ItemBuilder(Material.BRAIN_CORAL_BLOCK).setDisplayName("§e✦ §dParty 'n Friends™")
                .addLore("§e» §7Zeige deine Partykumpels ","§7und Freunde an§7.").build();
        this.slime = new ItemBuilder(Material.SLIME_BLOCK).setDisplayName("§e✦ §aLade Spieler")
                .addLore("§e» §7Lade wieder alle Spieler§7.").build();
        this.settingsLayout = loadLayout();
        this.friendLayout = loadFriendLayout();
    }

    private HashMap<Integer, ItemStack> loadLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        layout.put(0, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayName("§6Privatnachrichten").build());
        layout.put(1, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(3, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(4, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(5, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(9, new ItemBuilder(Material.FIREWORK_ROCKET).setDisplayName("§dParty").build());
        layout.put(10, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(12, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(13, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(14, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(18, new ItemBuilder(Material.GOLDEN_HELMET).setDisplayName("§bFreunde").
                addItemFlag(ItemFlag.HIDE_ATTRIBUTES).build());
        layout.put(19, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(21, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(22, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(23, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(27, new ItemBuilder(Material.SLIME_BALL).setDisplayName("§aNachspringen").build());
        layout.put(28, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build());
        layout.put(30, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von jedem").build());
        layout.put(31, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Freunden").build());
        layout.put(32, new ItemBuilder(Material.GRAY_DYE).setDisplayName("Von Niemanden").build());
        layout.put(53, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cSchließen").build());
        return layout;
    }

    private HashMap<Integer, ItemStack> loadFriendLayout() {
        HashMap<Integer, ItemStack> layout = new HashMap<>();
        ItemStack pane = new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName("§0").build();
        for (int i = 36; i < 45; i++) {
            layout.put(i, pane);
        }
        layout.put(45, new ItemBuilder(Material.EMERALD).setDisplayName("Freundesanfragen").build());
        layout.put(48, new CustomPlayerHeadBuilder().setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzdhZWU5YTc1YmYwZGY3ODk3MTgzMDE1Y2NhMGIyYTdkNzU1YzYzMzg4ZmYwMTc1MmQ1ZjQ0MTlmYzY0NSJ9fX0=", "" ).build());
        layout.put(50, new CustomPlayerHeadBuilder().setSkinOverValues("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjgyYWQxYjljYjRkZDIxMjU5YzBkNzVhYTMxNWZmMzg5YzNjZWY3NTJiZTM5NDkzMzgxNjRiYWM4NGE5NmUifX19","").build());
        layout.put(53, new ItemBuilder(Material.DARK_OAK_DOOR).setDisplayName("§cSchließen").build());
        return layout;
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(1, teleporter);
        player.getInventory().setItem(3, tnt);
        player.getInventory().setItem(5, panel);
    }

    public Map<Integer, ItemStack> getSettingsLayout() {
        return settingsLayout;
    }

    public Map<Integer, ItemStack> getFriendLayout() {
        return friendLayout;
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