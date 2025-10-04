package e45tm3d.pit.modules.tasks;

import org.bukkit.event.Event;

import java.util.Objects;

public abstract class TaskModule {

    public abstract void run(TaskModule task);

    public void register() {
        run(this);
        Tasks.registerTask(this);
    }
}