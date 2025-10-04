package e45tm3d.pit.modules.listeners.player;

import com.google.common.collect.Maps;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.listeners.ListenerModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class ArrowLoader extends ListenerModule {

    public static Map<UUID, ItemStack> lastItem = new HashMap<>();

    @Override
    public void listen(Event event) {

        if (event instanceof InventoryClickEvent e) {

            if (e.getWhoClicked() instanceof Player p) {

                if (!Objects.isNull(p.getInventory().getItem(9)) && !e.getSlotType().equals(InventoryType.SlotType.OUTSIDE)) {
                    if (p.getInventory().getItem(9).getType().equals(Material.ARROW)) {
                        if (e.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
                            if (e.getSlot() == 9) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        } else if (event instanceof PlayerItemHeldEvent e) {

            Player p = e.getPlayer();
            UUID uuid = p.getUniqueId();
            ItemStack newItem = p.getInventory().getItem(e.getNewSlot());

            if (newItem != null && newItem.getType() == Material.BOW) {
                ItemStack oldItemInSlot9 = p.getInventory().getItem(9);
                if (oldItemInSlot9 == null || oldItemInSlot9.getType() != Material.ARROW) {
                    lastItem.put(uuid, oldItemInSlot9);
                }
                p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
            } else {
                if (lastItem.containsKey(uuid)) {
                    p.getInventory().setItem(9, lastItem.get(uuid));
                    lastItem.remove(uuid);
                }
            }
        } else if (event instanceof EntityShootBowEvent e) {
            e.getProjectile().setMetadata("NO_PICKUP_METADATA", new FixedMetadataValue( ThePit.getInstance(), true));
            if (e.getEntity() instanceof Player p) {
                p.getInventory().setItem(9, new ItemStack(Material.ARROW, 64));
                e.getProjectile().setTicksLived(1200);
            }
        }
    }

    public static void restoreAllPlayerItemSlots() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            if (lastItem.containsKey(uuid)) {
                p.getInventory().setItem(9, lastItem.get(uuid));
            }
        }
        lastItem.clear();
    }
}
