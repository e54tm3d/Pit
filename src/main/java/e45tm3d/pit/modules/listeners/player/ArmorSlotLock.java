package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class ArmorSlotLock extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof InventoryClickEvent e) {
            if (e.getWhoClicked() instanceof Player p) {
                if (PlayerFunction.isInArena(p)) {
                    if (e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}