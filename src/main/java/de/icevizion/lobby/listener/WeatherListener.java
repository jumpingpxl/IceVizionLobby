package de.cosmiqglow.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    @EventHandler
    public void on(WeatherChangeEvent event) {
        event.setCancelled(event.toWeatherState());
    }
}
