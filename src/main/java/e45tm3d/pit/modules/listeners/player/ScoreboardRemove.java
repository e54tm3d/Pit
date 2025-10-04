package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.modules.tasks.player.Scoreboard;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardRemove extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerQuitEvent e) {
            Player p = e.getPlayer();
            Scoreboard.removePlayerScoreboard(p);
        }
    }
}