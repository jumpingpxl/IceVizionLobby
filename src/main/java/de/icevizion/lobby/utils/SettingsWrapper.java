package de.icevizion.lobby.utils;

public enum SettingsWrapper {

    PRIVATE_MESSAGE(100, 3),
    PARTY(101, 3),
    PLAYER_VISIBILITY(102, 3),
    JUMP(103, 2),
    EVENT(199, 2);

    final int settingsID;
    final int values;

    SettingsWrapper(int settingsID, int values) {
        this.settingsID = settingsID;
        this.values = values;
    }

    public int getSettingsID() {
        return settingsID;
    }

    public int getValues() {
        return values;
    }
}