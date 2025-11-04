package e45tm3d.pit.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerRightClickEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final RightClickType clickType;
    private final ItemStack holdingItem;
    private final Object target;
    private boolean cancelled = false;

    public enum RightClickType {
        AIR,
        BLOCK,
        ENTITY
    }

    public PlayerRightClickEvent(Player player, RightClickType clickType, ItemStack holdingItem, Object target) {
        super(true); // 异步事件（数据包处理为异步，需指定为异步事件）
        this.player = player;
        this.clickType = clickType;
        this.holdingItem = holdingItem;
        this.target = target;
    }

    public Player getPlayer() {
        return player;
    }

    public RightClickType getClickType() {
        return clickType;
    }

    public ItemStack getItem() {
        return holdingItem;
    }

    public Object getTarget() {
        return target;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    // Bukkit 事件必须的方法
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}