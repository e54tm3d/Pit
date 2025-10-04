package e45tm3d.pit.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityFrozenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    public Entity entity;
    public Entity damager;

    public EntityFrozenEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getDamager() {
        return damager;
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
