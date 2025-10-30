package e45tm3d.pit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerObtainWeaponEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public Player player;
    public String item;
    public ItemStack itemStack;

    public PlayerObtainWeaponEvent(Player player, String item, ItemStack itemStack) {
        this.item = item;
        this.itemStack = itemStack;
        this.player = player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getItem() {
        return item;
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
