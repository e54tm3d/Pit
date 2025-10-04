package e45tm3d.pit.modules.tasks.world;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;

public class TimeLock extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (Yaml.CONFIG.getConfig().getBoolean("worlds.world_manager." + VariableFunction.getActiveArena() + ".time_lock.enabled")) {
                Bukkit.getWorld(VariableFunction.getActiveArena()).setTime(
                        Yaml.CONFIG.getConfig().getInt("worlds.world_manager." + VariableFunction.getActiveArena() + ".time_lock.time"));
            }
        }, 0, 20);
    }
}
