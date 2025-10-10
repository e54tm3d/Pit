package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.listeners.ListenerModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemCleaner extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerDropItemEvent e) {

            Item item = e.getItemDrop();

            Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), item::remove, 1200);

        } else if (event instanceof ProjectileLaunchEvent e) {

            Projectile projectile = e.getEntity();

            Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), projectile::remove, 1200);
        }
    }
}
