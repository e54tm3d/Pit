package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.DatabaseFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class DatabaseLoader extends ListenerModule {

    @Override
    public void listen(Event event) {

        if (event instanceof PlayerJoinEvent e) {
            Player p = e.getPlayer();

            if (p.hasPlayedBefore()) {
                Bukkit.getScheduler().runTaskAsynchronously(ThePit.getInstance(), () -> {
                    DatabaseFunction.setupDatabaseInRam(p);
                });
            }
        }
    }
}