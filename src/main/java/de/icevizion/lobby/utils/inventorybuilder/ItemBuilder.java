package de.icevizion.lobby.utils.inventorybuilder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class ItemBuilder {

	private final ItemStack itemStack;
	private final ItemMeta itemMeta;

	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.itemMeta = itemStack.getItemMeta();
	}

	public ItemBuilder(Material material) {
		this(new ItemStack(material));
	}

	public ItemBuilder(String textureValue) {
		ItemStack skullItemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta skullMeta = (SkullMeta) skullItemStack.getItemMeta();
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
		gameProfile.getProperties().put("textures", new Property("textures", textureValue));
		try {
			Field field = skullMeta.getClass().getDeclaredField("profile");
			field.setAccessible(true);
			field.set(skullMeta, gameProfile);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}

		skullItemStack.setItemMeta(skullMeta);
		this.itemStack = skullItemStack;
		this.itemMeta = skullMeta;
	}

	public ItemBuilder setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}

	public ItemBuilder setDurability(short durability) {
		itemStack.setDurability(durability);
		return this;
	}

	public ItemBuilder setDisplayName(String displayName) {
		itemMeta.setDisplayName(displayName);
		return this;
	}

	public ItemBuilder setDisplayName(Translator translator, Locale locale, String key,
	                                  Object... arguments) {
		return setDisplayName(translator.getString(locale, key, arguments));
	}

	public ItemBuilder setDisplayName(Translator translator, CloudPlayer cloudPlayer, String key,
	                                  Object... arguments) {
		return setDisplayName(translator, cloudPlayer.getLocale(), key, arguments);
	}

	public ItemBuilder setLore(String... lore) {
		itemMeta.setLore(Arrays.asList(lore));
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		itemMeta.setLore(lore);
		return this;
	}

	public ItemBuilder setLore(Translator translator, Locale locale, String key,
	                           Object... arguments) {
		String[] lore = translator.getString(locale, key, arguments).split("\n");
		List<String> loreList = Arrays.asList(lore);
		return setLore(loreList);
	}

	public ItemBuilder setLore(Translator translator, CloudPlayer cloudPlayer, String key,
	                           Object... arguments) {
		return setLore(translator, cloudPlayer.getLocale(), key, arguments);
	}

	public ItemBuilder setUnbreakable(boolean unbreakable) {
		itemMeta.spigot().setUnbreakable(unbreakable);
		return this;
	}

	public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
		itemMeta.addItemFlags(itemFlags);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		itemMeta.addEnchant(enchantment, level, true);
		return this;
	}

	public ItemStack build() {
		addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemStack.setItemMeta(itemMeta);
		return itemStack;
	}
}

