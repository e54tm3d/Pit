package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class ItemProtect extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerDropItemEvent e) {

            ItemStack item = e.getItemDrop().getItemStack();

            for (String weapon : ItemFunction.searchWeapons()) {
                if (ItemFunction.isItem(item, weapon)) {
                    e.setCancelled(true);
                    return;
                }
            }

            for (String amulets : ItemFunction.searchAmulets()) {
                if (ItemFunction.isItem(item, amulets)) {
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }
}
