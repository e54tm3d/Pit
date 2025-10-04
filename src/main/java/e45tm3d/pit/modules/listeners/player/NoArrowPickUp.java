package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class NoArrowPickUp extends ListenerModule {

    public static double gold;

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerPickupItemEvent e) {

            Player p = e.getPlayer();

            if (PlayerFunction.isInArena(p)) {
                if (e.getItem().hasMetadata("NO_PICKUP_METADATA")) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
