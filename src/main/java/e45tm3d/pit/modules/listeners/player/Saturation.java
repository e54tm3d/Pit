package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class Saturation extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof FoodLevelChangeEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (PlayerFunction.isInArena(p)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
