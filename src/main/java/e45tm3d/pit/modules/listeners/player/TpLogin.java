package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

public class TpLogin extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerJoinEvent e) {
            Player p = e.getPlayer();

            if (VariableFunction.isSpawnExists()) {
                p.teleport(VariableFunction.getSpawnLocation());
            }
        }
    }
}
