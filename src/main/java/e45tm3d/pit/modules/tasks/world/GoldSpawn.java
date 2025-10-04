package e45tm3d.pit.modules.tasks.world;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class GoldSpawn extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (Yaml.CONFIG.getConfig().getBoolean("settings.gain_gold.enable")) {
                if (!Bukkit.getOnlinePlayers().isEmpty()) {
                    int i = (int) MathFunction.randomDouble(1000, 1);

                    Location center = new Location(Bukkit.getWorld(VariableFunction.getActiveArena())
                            , Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.gold_spawn.spawn_location.x")
                            , Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.gold_spawn.spawn_location.y")
                            , Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.gold_spawn.spawn_location.z"));

                    Location loc = VariableFunction.getRandomLocation(center, Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.gold_spawn.radius"),
                            -Yaml.CONFIG.getConfig().getDouble("settings.gain_gold.gold_spawn.radius"));
                    ItemStack gold_nugget = new ItemStack(Material.GOLD_NUGGET, 1);
                    gold_nugget = ItemFunction.addNBTTag(gold_nugget, UUID.randomUUID().toString());
                    if (i > 25) {
                        org.bukkit.entity.Item gold = loc.getWorld().dropItem(loc, gold_nugget);
                        gold.setTicksLived(6000);
                    } else {
                        loc.getWorld().dropItem(loc, ItemFunction.searchItem("golden_ingot"));
                    }
                }
            }
        }, 240, 240);
    }
}
