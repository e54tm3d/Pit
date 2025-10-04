package e45tm3d.pit.modules.curse.curses;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.CurseModule;
import e45tm3d.pit.utils.enums.curse.CurseItems;
import e45tm3d.pit.utils.enums.curse.CurseMenuItems;
import e45tm3d.pit.utils.functions.PlayerFunction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class KnockbackEnchance extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.KNOCKBACK_ENCHANCE.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.KNOCKBACK_ENCHANCE.getItemStack();
    }

    @Override
    public String getType() {
        return "knockback_enchance";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.knockback_enchance.price", 100000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.knockback_enchance.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.knockback_enchance.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.knockback_enchance.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getEntity() instanceof Player p) {

                double originalX = e.getDamager().getLocation().getX();
                double originalY = e.getDamager().getLocation().getY();
                double originalZ = e.getDamager().getLocation().getZ();

                Location front = PlayerFunction.getBlockFrontOfEntity(e.getDamager(), 1.5);

                double deltaX = front.getX() - originalX;
                double deltaY = front.getY() - originalY;
                double deltaZ = front.getZ() - originalZ;

                if (isEquiped(p)) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(ThePit.getInstance(), () -> {

                        Vector vec = new Vector(deltaX, deltaY, deltaZ);

                        vec = vec.setY(0.42);
                        p.setVelocity(vec);
                    }, 1);
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {

    }
}
