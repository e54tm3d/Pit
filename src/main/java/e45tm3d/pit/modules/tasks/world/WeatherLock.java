package e45tm3d.pit.modules.tasks.world;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.WeatherType;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class WeatherLock extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            World world = Bukkit.getWorld(VariableFunction.getActiveArena());
                world.setStorm(Yaml.CONFIG.getConfig().getBoolean("worlds.world_manager." + VariableFunction.getActiveArena() + ".weather.storm"));
                world.setThundering(Yaml.CONFIG.getConfig().getBoolean("worlds.world_manager." + VariableFunction.getActiveArena() + ".weather.thundering"));
                for (Player p : world.getPlayers()) {
                    WeatherType type = WeatherType.CLEAR;
                    if (Yaml.CONFIG.getConfig().getString("worlds.world_manager." + VariableFunction.getActiveArena() + ".weather.storm")
                            .equalsIgnoreCase("DOWNFALL")) {
                        type = WeatherType.DOWNFALL;
                    }
                    p.setPlayerWeather(type);
                }
        },0, 20);
    }
}
