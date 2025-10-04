package e45tm3d.pit.modules.listeners;

import org.bukkit.event.Event;

import java.util.Objects;

public abstract class ListenerModule {

    public abstract void listen(Event event);

    public void register() {
        Listeners.registerListener(this);
    }
}
