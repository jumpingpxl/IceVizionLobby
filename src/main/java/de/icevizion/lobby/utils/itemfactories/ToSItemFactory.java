package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ColoredBuilder;
import de.icevizion.aves.item.ItemBuilder;
import net.titan.cloudcore.i18n.Translator;
import org.bukkit.DyeColor;
import org.bukkit.Material;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ToSItemFactory extends InventoryItemFactory {

	public ToSItemFactory(Translator translator, Locale locale) {
		super(translator, locale);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getInfoItem() {
		return createItem(new ItemBuilder(Material.BOOK)).setDisplayName("tosInfoName").setLore(
				"tosInfoLore");
	}

	public InventoryItem getAcceptItem() {
		return createItem(new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(
				DyeColor.GREEN)).setDisplayName("tosAcceptName").setLore("tosAcceptLore");
	}

	public InventoryItem getDenyItem() {
		return createItem(new ColoredBuilder(ColoredBuilder.DyeType.CLAY_BLOCK).setColor(
				DyeColor.GREEN)).setDisplayName("tosDenyName").setLore("tosDenyLore");
	}
}
