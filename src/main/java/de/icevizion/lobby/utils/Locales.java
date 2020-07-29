package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.cloudcore.i18n.Translator;

import java.util.Locale;

/**
 * @author Nico (JumpingPxl) Middendorf
 */

public class Locales extends Translator {

	public Locales(LobbyPlugin lobbyPlugin) {
		super(lobbyPlugin.getClass().getClassLoader(), "lobby");
	}

	@Override
	public void loadLocales() {
		addResourceBundle(Locale.GERMAN);
	}
}
