package e45tm3d.pit.modules.curse.curses;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.CurseModule;
import e45tm3d.pit.utils.enums.curse.CurseItems;
import e45tm3d.pit.utils.enums.curse.CurseMenuItems;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ImpregnableFortress extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.IMPREGNABLE_FORTRESS.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.IMPREGNABLE_FORTRESS.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "impregnable_fortress";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.impregnable_fortress.price", 200000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.impregnable_fortress.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.impregnable_fortress.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.impregnable_fortress.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (isEquiped(p)) {
                    e.setDamage(Math.min(e.getDamage(), p.getMaxHealth() * 0.1));
                }
            }
            if (e.getEntity() instanceof Player p) {
                if (isEquiped(p)) {
                    e.setDamage(Math.min(e.getDamage(), p.getMaxHealth() * 0.15));
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {

    }
}