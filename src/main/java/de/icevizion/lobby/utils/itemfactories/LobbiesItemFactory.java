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

public class LobbiesItemFactory extends InventoryItemFactory {

	public LobbiesItemFactory(Translator translator, Locale locale) {
		super(translator, locale);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getLobbyItem(String serverName, Integer playerCount) {
		return createItem(new ItemBuilder(Material.GLOWSTONE_DUST)).setDisplayName("lobbyName",
				serverName).setLore("lobbyLore", playerCount);
	}

	public InventoryItem getCurrentLobbyItem(String serverName, Integer playerCount) {
		return createItem(new ItemBuilder(Material.GLOWSTONE_DUST).addEnchantment(Enchantment.KNOCKBACK,
				1).addItemFlag(ItemFlag.HIDE_ENCHANTS)).setDisplayName("currentLobbyName", serverName)
				.setLore("currentLobbyLore", playerCount);
	}
}
