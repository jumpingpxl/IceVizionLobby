package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.cloudcore.i18n.Translator;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class GamesItemFactory extends InventoryItemFactory {

	public GamesItemFactory(Translator translator, Locale locale) {
		super(translator, locale);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getSpawnItem() {
		return createItem(new ItemBuilder(Material.NETHER_STAR)).setDisplayName("spawnName");
	}

	public InventoryItem getGuessItItem() {
		return createItem(new ItemBuilder(Material.BOOK_AND_QUILL)).setDisplayName("guessItName");
	}

	public InventoryItem getKnockBackFFAItem() {
		return createItem(new ItemBuilder(Material.STICK).addEnchantment(Enchantment.KNOCKBACK, 1)
				.addItemFlag(ItemFlag.HIDE_ENCHANTS)).setDisplayName("kbffaName");
	}

	public InventoryItem getOneLineItem() {
		return createItem(new ItemBuilder(Material.SANDSTONE)).setDisplayName("oneLineName");
	}

	public InventoryItem getBedWarsItem() {
		return createItem(new ItemBuilder(Material.BED)).setDisplayName("bedWarsName");
	}
}
