package e45tm3d.pit.modules.curse.curses;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.CurseModule;
import e45tm3d.pit.utils.enums.curse.CurseItems;
import e45tm3d.pit.utils.enums.curse.CurseMenuItems;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Bloodthirsty extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.BLOODTHIRSTY.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.BLOODTHIRSTY.getItemStack();
    }

    @Override
    public String getType() {
        return "bloodthirsty";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.bloodthirsty.price", 150000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.bloodthirsty.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloodthirsty.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloodthirsty.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (isEquiped(p)) {
                    double distance = p.getLocation().distance(e.getEntity().getLocation());
                    e.setDamage(Math.min(e.getDamage() * 1.5, e.getDamage() * (8 / distance)));
                }
            } else if (e.getDamager() instanceof Projectile pro) {
                if (pro.getShooter() instanceof Player p) {
                    if (isEquiped(p)) {
                        double distance = p.getLocation().distance(e.getEntity().getLocation());
                        e.setDamage(Math.min(e.getDamage() * 1.5, e.getDamage() * (8 / distance)));
                    }
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {

    }
}
