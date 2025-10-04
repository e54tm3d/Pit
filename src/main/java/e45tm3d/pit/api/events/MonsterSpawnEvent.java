package e45tm3d.pit.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MonsterSpawnEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    public Entity entity;
    public String type;
    public String name;
    public Location loc;

    public MonsterSpawnEvent(Entity entity, String type, String name, Location loc) {
        this.entity = entity;
        this.type = type;
        this.name = name;
        this.loc = loc;
    }

    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
