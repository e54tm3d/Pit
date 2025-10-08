package e45tm3d.pit.modules.tasks;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.tasks.player.*;
import e45tm3d.pit.modules.tasks.world.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;

public class Tasks {

    private static List<TaskModule> tasks = new ArrayList<>();

	public Tasks() {
        ThePit.getInstance().getLogger().info("Loading task module...");

        tasks = Lists.newArrayList(new BlockBreak(), new GoldSpawn(), new DevelopMode(), new Tablist(), new Scoreboard(), new LevelUpdate(),
                new ArrowsClear(), new TimeLock(), new WeatherLock());

        List<TaskModule> copy = Lists.newArrayList(tasks);
        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Task module loaded successfully!");
    }

    private void register(TaskModule task) {
        task.register();
    }
}