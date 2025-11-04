package e45tm3d.pit.api.events;

import e45tm3d.pit.api.Preplayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerConnectEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public Preplayer preplayer;

    public PlayerConnectEvent(Preplayer preplayer) {
        this.preplayer = preplayer;
    }

    public Preplayer getPreplayer() {
        return preplayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
