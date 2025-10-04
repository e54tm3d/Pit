package e45tm3d.pit.modules.tasks.world;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;

public class ArrowsClear extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Entity entity : Bukkit.getWorld(VariableFunction.getActiveArena()).getEntities()) {
                if (entity instanceof Projectile projectile) {
                    if (projectile.isOnGround()) {
                        projectile.remove();
                    }
                }
            }
        }, 1200, 1200);
    }
}
