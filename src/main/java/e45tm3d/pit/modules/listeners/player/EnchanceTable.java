package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.menus.normal_menus.EnchanceMenu;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnchanceTable extends ListenerModule {
    @Override
    public void listen(Event event) {
        if (event instanceof PlayerInteractEvent e) {
            if (User.isPlaying(e.getPlayer())) {
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (e.getClickedBlock().getType().equals(Material.ENCHANTMENT_TABLE)) {
                        e.setCancelled(true);
                        EnchanceMenu.open(e.getPlayer());
                    }
                }
            }
        }
    }
}
