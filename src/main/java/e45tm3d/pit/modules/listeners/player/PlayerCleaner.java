package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.InventoryFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerCleaner extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerQuitEvent e) {
            Player p = e.getPlayer();

            PlayerLists.playing.remove(p.getUniqueId());
            PlayerMaps.spectating_selected.remove(p.getUniqueId());

            PlayerMaps.night_vision.remove(p.getUniqueId());
            PlayerMaps.speed.remove(p.getUniqueId());
            PlayerMaps.jump_boost.remove(p.getUniqueId());
            PlayerMaps.flight.remove(p.getUniqueId());
            PlayerMaps.always_flight.remove(p.getUniqueId());

            if (User.isPlaying(p)) {
                InventoryFunction.saveInventory(p);
            }
            InventoryFunction.loadInventory(p);
            InventoryFunction.clearCache(p);
        }
    }
}
