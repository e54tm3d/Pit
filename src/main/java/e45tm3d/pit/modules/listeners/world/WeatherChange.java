package e45tm3d.pit.modules.listeners.world;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof WeatherChangeEvent e) {

            String path = "world_manager." + VariableFunction.getActiveArena();

            if (e.getWorld().equals(Bukkit.getWorld(VariableFunction.getActiveArena()))) {
                e.setCancelled(Yaml.CONFIG.getConfig().getBoolean(path + ".weather.change"));
                e.getWorld().setStorm(Yaml.CONFIG.getConfig().getBoolean(path + ".weather.storm"));
                e.getWorld().setThundering(Yaml.CONFIG.getConfig().getBoolean(path + ".weather.thundering"));
                for (Player p : e.getWorld().getPlayers()) {
                    WeatherType type = WeatherType.CLEAR;
                    if (Yaml.CONFIG.getConfig().getString(path + ".weather.storm")
                            .equalsIgnoreCase("DOWNFALL")) type = WeatherType.DOWNFALL;
                    if (Yaml.CONFIG.getConfig().getString(path + ".weather.storm")
                            .equalsIgnoreCase("CLEAR")) type = WeatherType.CLEAR;
                    p.setPlayerWeather(type);
                }
            }
        }
    }
}
