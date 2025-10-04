package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.DatabaseFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class LevelLoader extends ListenerModule {

    @Override
    @Deprecated
    public void listen(Event event) {
        if (event instanceof PlayerCommandPreprocessEvent e) {
            if (e.getPlayer().hasPermission("pit.admin")) {
                if (e.getMessage().startsWith("/level")) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), () -> {
                        Player p = e.getPlayer();
                        int level = DatabaseFunction.getLevel(p);
                        User.setLevel(p, level);
                    }, 10);
                } else if (e.getMessage().startsWith("/exp")) {
                    Bukkit.getScheduler().scheduleAsyncDelayedTask(ThePit.getInstance(), () -> {
                        Player p = e.getPlayer();
                        int exp = DatabaseFunction.getExp(p);
                        User.setExp(p, exp);
                    }, 10);
                }
            }
        }
    }
}
