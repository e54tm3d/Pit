package e45tm3d.pit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUpgradeWeaponEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;
    public Player player;
    public String weapon;
    public int newLevel;
    public int oldLevel;

    public PlayerUpgradeWeaponEvent(Player player, String weapon, int newLevel, int oldLevel) {
        this.weapon = weapon;
        this.player = player;
        this.newLevel = newLevel;
        this.oldLevel = oldLevel;
    }

    public String getBuff() {
        return weapon;
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