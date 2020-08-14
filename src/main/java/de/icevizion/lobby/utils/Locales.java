package de.icevizion.lobby.utils;

import de.icevizion.lobby.LobbyPlugin;
import net.titan.cloudcore.i18n.Translator;
import net.titan.spigot.player.CloudPlayer;

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

	public String translateTimestampDifference(CloudPlayer cloudPlayer, long timestamp) {
		long seconds = (System.currentTimeMillis() - timestamp) / 1000;
		if(seconds < 60) {
			return getString(cloudPlayer, "differSeconds");
		}

		long minutes = seconds / 60;
		if(minutes < 60) {
			return getString(cloudPlayer, "differMinutes", minutes);
		}

		long hours = minutes / 60;
		if(hours < 24) {
			return getString(cloudPlayer, "differHours", hours);
		}

		long days = hours / 24;
		return getString(cloudPlayer, "differDays", days);
	}
}
