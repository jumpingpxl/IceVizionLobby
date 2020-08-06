package de.icevizion.lobby.utils.inventorybuilder;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class InventoryBuilder {

	private final String title;
	private final int size;
	private final Map<Integer, Consumer<InventoryClickEvent>> clickEvents;
	private final Map<Integer, ItemBuilder> items;
	private final Map<Integer, ItemBuilder> backGroundItems;
	private Inventory inventory;
	private boolean firstDraw;

	public InventoryBuilder(String title, int size) {
		items = Maps.newHashMap();
		backGroundItems = Maps.newHashMap();
		clickEvents = Maps.newHashMap();
		this.title = title;
		this.size = size;
		firstDraw = true;
	}

	public void draw() {
		//set this in the inventory class
	}

	public boolean onInventoryClose(InventoryCloseEvent event) {
		return false;
	}

	public boolean isCacheable() {
		return false;
	}

	public final void setItem(int index, ItemBuilder itemBuilder) {
		items.put(index, itemBuilder);
	}

	public final void setItem(int index, ItemBuilder itemBuilder,
	                          Consumer<InventoryClickEvent> event) {
		setItem(index, itemBuilder);
		clickEvents.put(index, event);
	}

	public final void setBackgroundItem(int index, ItemBuilder itemBuilder) {
		backGroundItems.put(index, itemBuilder);
	}

	public final void setBackgroundItem(ItemBuilder itemBuilder) {
		for (int i = 0; i < size; i++) {
			setBackgroundItem(i, itemBuilder);
		}
	}

	public final Inventory getInventory() {
		return inventory;
	}

	public final ItemBuilder getItem(int slot) {
		return items.get(slot);
	}

	public final boolean isFirstDraw() {
		if (firstDraw) {
			firstDraw = false;
			return true;
		}

		return false;
	}

	public final Map<Integer, ItemBuilder> getItems() {
		return items;
	}

	public final Map<Integer, Consumer<InventoryClickEvent>> getClickEvents() {
		return clickEvents;
	}

	public final InventoryBuilder buildInventory() {
		if (inventory == null) {
			inventory = Bukkit.createInventory(null, size, title);
		}

		draw();
		setItems();
		return this;
	}

	public final void setItems() {
		if (firstDraw) {
			backGroundItems.forEach((key, value) -> inventory.setItem(key, value.build()));
		}

		for (int i = 0; i < inventory.getContents().length; i++) {
			if (!backGroundItems.containsKey(i) && !items.containsKey(i)) {
				clickEvents.remove(i);
				inventory.remove(i);
			}
		}

		items.forEach((key, value) -> inventory.setItem(key,
				value == null ? new ItemStack(Material.AIR) : value.build()));
	}

	public final void setFirstDraw(boolean firstDraw) {
		this.firstDraw = firstDraw;
	}
}
