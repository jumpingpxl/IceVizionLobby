package de.icevizion.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

	@EventHandler
	public void onWeather(WeatherChangeEvent event) {
		event.setCancelled(event.toWeatherState());
	}
}
