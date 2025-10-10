package e45tm3d.pit.modules.tasks;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.modules.monsters.bosses.SlimeKing;
import e45tm3d.pit.modules.monsters.monsters.LavaSlime;
import e45tm3d.pit.modules.monsters.monsters.LightningCreeper;
import e45tm3d.pit.modules.monsters.monsters.Skeleton;
import e45tm3d.pit.modules.monsters.monsters.Slime;
import e45tm3d.pit.modules.tasks.player.*;
import e45tm3d.pit.modules.tasks.world.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

public class Tasks {

    private static List<TaskModule> tasks = new ArrayList<>();

	public Tasks() {
        ThePit.getInstance().getLogger().info("Loading task module...");

        tasks.clear();

        tasks = Lists.newArrayList(new BlockBreak(), new GoldSpawn(), new DevelopMode(), new Tablist(), new Scoreboard(), new LevelUpdate(),
                new TimeLock(), new WeatherLock());

        List<TaskModule> copy = Lists.newArrayList(tasks);
        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Task module loaded successfully!");
    }

    private void register(TaskModule task) {
        task.register();
    }
}