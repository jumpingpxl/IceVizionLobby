package de.icevizion.lobby.utils;

public enum SettingsWrapper {

    PRIVATE_MESSAGE(100, 3),
    PARTY(101, 3),
    PLAYER_VISIBILITY(102, 3),
    JUMP(103, 2),
    EVENT(199, 2);

    final int settingsID;
    final int value;

    SettingsWrapper(int settingsID, int value) {
        this.settingsID = settingsID;
        this.value = value;
    }

    public int getSettingsID() {
        return settingsID;
    }

    public int getValue() {
        return value;
    }
}