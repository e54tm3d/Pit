package e45tm3d.pit.api.events;

import e45tm3d.pit.modules.enchance.EnchanceType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerEnchanceEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    public Player player;
    public EnchanceType enchanceType;
    public String enchance;
    public boolean failed;

    public PlayerEnchanceEvent(Player player, EnchanceType enchanceType, String enchance, boolean failed) {
        this.player = player;
        this.enchanceType = enchanceType;
        this.enchance = enchance;
        this.failed = failed;
    }

    public boolean isFailed() {
        return failed;
    }

    public String getEnchance() {
        return enchance;
    }

    public EnchanceType getEnchanceType() {
        return enchanceType;
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
