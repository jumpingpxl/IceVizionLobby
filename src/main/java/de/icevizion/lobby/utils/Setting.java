package de.icevizion.lobby.utils;

import org.bukkit.Material;

public enum Setting {

	PRIVATE_MESSAGE("settingsPrivateMessage", Material.BOOK_AND_QUILL, 100, 3, (short) 10,
			(short) 14,
			(short) 1),
	PARTY_INVITE("settingsPartyInvite", Material.FIREWORK, 101, 3, (short) 10, (short) 14,
			(short) 1),
	FRIEND_JUMP("settingsFriendJump", Material.ENDER_PEARL, 103, 2, (short) 14,
			(short) 1),
	PLAYER_VISIBILITY("settingsVisibility", Material.BLAZE_ROD, 102, 3, (short) 10, (short) 14, (short) 1);

	private final String key;
	private final Material material;
	private final int id;
	private final int value;
	private final short[] colors;

	Setting(String key, Material material, int id, int value, short... colors) {
		this.key = key;
		this.material = material;
		this.id = id;
		this.value = value;
		this.colors = colors;
	}

	public String getKey() {
		return key;
	}

	public Material getMaterial() {
		return material;
	}

	public int getId() {
		return id;
	}

	public int getValue() {
		return value;
	}

	public short[] getColors() {
		return colors;
	}
}