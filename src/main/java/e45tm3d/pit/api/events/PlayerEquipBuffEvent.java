package e45tm3d.pit.api.events;

import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEquipBuffEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    public Player player;
    public String buff;
    public int slot;

    public PlayerEquipBuffEvent(Player player, String buff, int slot) {
        this.player = player;
        this.buff = buff;
        this.slot = slot;
    }

    public String getBuff() {
        return buff;
    }

    public int getSlot() {
        return slot;
    }

    public Player getPlayer() {
        return player;
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
