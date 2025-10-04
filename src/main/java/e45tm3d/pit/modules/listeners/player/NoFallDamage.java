package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamage extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageEvent e) {

            if (e.getEntity() instanceof Player p) {
                if (PlayerFunction.isInArena(p)) {
                    if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
