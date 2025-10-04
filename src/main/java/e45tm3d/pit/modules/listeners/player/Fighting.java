package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Fighting extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {

            if (!e.isCancelled()) {
                if (e.getDamager() instanceof Player p) {

                    if (!e.isCancelled() && !PlayerFunction.isInSpawn(p)) {
                        if (PlayerMaps.fight_time.containsKey(p.getUniqueId())) {
                            PlayerMaps.fight_time.put(p.getUniqueId(), System.currentTimeMillis());
                        } else {
                            PlayerMaps.fight_time.put(p.getUniqueId(), 0L);
                        }
                    }
                }
                if (e.getEntity() instanceof Player p) {
                    if (!e.isCancelled() && !PlayerFunction.isInSpawn(p)) {
                        if (PlayerMaps.fight_time.containsKey(p.getUniqueId())) {
                            PlayerMaps.fight_time.put(p.getUniqueId(), System.currentTimeMillis());
                        } else {
                            PlayerMaps.fight_time.put(p.getUniqueId(), 0L);
                        }
                    }
                }
            }
        } else if (event instanceof PlayerQuitEvent e) {
            Player p = e.getPlayer();
            e.setQuitMessage(null);
            if (User.isFighting(p)) {
                User.setDeaths(e.getPlayer(), User.getDeaths(e.getPlayer()) + 1);
                User.setKillstreak(e.getPlayer(), 0);
            }
        }
    }
}