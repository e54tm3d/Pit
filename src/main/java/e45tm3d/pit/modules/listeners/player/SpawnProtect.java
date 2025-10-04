package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SpawnProtect extends ListenerModule {

    @Override
    public void listen(Event event) {

        if (event instanceof EntityDamageByEntityEvent e) {

            if (e.getDamager() instanceof Player p) {

                if (PlayerFunction.isInArena(p)) {
                    if (PlayerFunction.isInArena(p)) {
                        if (PlayerFunction.isInSpawn(p)) {
                            e.setCancelled(true);
                        }
                    }
                }
            }

            if (e.getEntity() instanceof Player p) {
                if (PlayerFunction.isInArena(p)) {
                    if (PlayerFunction.isInSpawn(p)) {
                        e.setCancelled(true);
                    }
                }
            }
        } else if (event instanceof FoodLevelChangeEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (VariableFunction.isInSpawn(p.getLocation())) {
                    p.setFoodLevel(20);
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof BlockPlaceEvent e) {
            if (!PlayerFunction.isDevelopMode(e.getPlayer())) {
                if (VariableFunction.isInSpawn(e.getBlock().getLocation())
                        || e.getBlock().getY() > VariableFunction.getMaxBuildHeight()) {
                    e.setCancelled(true);
                    Messages.DENY_PLACE.sendMessage(e.getPlayer()).cooldown(3000);
                }
            }
        } else if (event instanceof EntityDamageEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (VariableFunction.isInSpawn(p.getLocation())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
