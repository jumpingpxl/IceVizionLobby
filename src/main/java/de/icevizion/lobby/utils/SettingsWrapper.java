package de.icevizion.lobby.utils;

public enum SettingsWrapper {

	PRIVATE_MESSAGE(100, 3),
	PARTY(101, 3),
	PLAYER_VISIBILITY(102, 3),
	JUMP(103, 2);

	final int id;
	final int value;

	SettingsWrapper(int id, int value) {
		this.id = id;
		this.value = value;
	}

	public int getID() {
		return id;
	}

	public int getValue() {
		return value;
	}
}