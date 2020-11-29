package de.icevizion.lobby.utils.itemfactories;

import de.icevizion.aves.inventory.InventoryItem;
import de.icevizion.aves.inventory.InventoryItemFactory;
import de.icevizion.aves.item.ColoredBuilder;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.DyeColor;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class DailyRewardItemFactory extends InventoryItemFactory {

	public DailyRewardItemFactory(Translator translator, CloudPlayer cloudPlayer) {
		super(translator, cloudPlayer);

		setBackgroundItem(
				new ColoredBuilder(ColoredBuilder.DyeType.GLASS_PANE).setColor(DyeColor.LIGHT_BLUE));
	}

	public InventoryItem getRewardItem() {
		return createItem(
				new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.LIME)).setDisplayName(
				"dailyName");
	}

	public InventoryItem getClaimedRewardItem() {
		return createItem(
				new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY)).setDisplayName(
				"dailyClaimedName").setLore("dailyClaimedLore");
	}

	public InventoryItem getPremiumRewardItem() {
		return createItem(
				new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.ORANGE)).setDisplayName(
				"dailyPremiumName");
	}

	public InventoryItem getClaimedPremiumRewardItem() {
		return createItem(
				new ColoredBuilder(ColoredBuilder.DyeType.DYE).setColor(DyeColor.GRAY)).setDisplayName(
				"dailyClaimedPremiumName").setLore("dailyClaimedPremiumLore");
	}
}
