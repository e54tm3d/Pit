package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class DataClear extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerQuitEvent e) {
            Player p = e.getPlayer();
            UUID uuid = p.getUniqueId();

            PlayerMaps.menu.remove(uuid);
            PlayerMaps.fight_time.remove(uuid);
            PlayerMaps.level.remove(uuid);
            PlayerMaps.exp.remove(uuid);
            PlayerMaps.kills.remove(uuid);
            PlayerMaps.deaths.remove(uuid);
            PlayerMaps.killstreak.remove(uuid);
            PlayerMaps.helmet_level.remove(uuid);
            PlayerMaps.chestplate_level.remove(uuid);
            PlayerMaps.leggings_level.remove(uuid);
            PlayerMaps.boots_level.remove(uuid);
            PlayerMaps.sword_level.remove(uuid);
            PlayerMaps.curse.remove(uuid);
            PlayerMaps.equip_curse.remove(uuid);
            PlayerMaps.selected_curse.remove(uuid);
            PlayerMaps.buff.remove(uuid);
            PlayerMaps.equip_buff.remove(uuid);
            PlayerMaps.selected_buff.remove(uuid);
            PlayerMaps.enchance.remove(uuid);
        }
    }
}
