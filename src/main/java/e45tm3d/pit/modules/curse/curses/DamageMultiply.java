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

public class DamageMultiply extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.DAMAGE_MULTIPLY.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.DAMAGE_MULTIPLY.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "damage_multiply";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.damage_multiply.price", 120000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.damage_multiply.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.damage_multiply.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.damage_multiply.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (isEquiped(p)) {
                    e.setDamage(e.getDamage() * 2);
                }
            }
            if (e.getEntity() instanceof Player p) {
                if (isEquiped(p)) {
                    e.setDamage(e.getDamage() * 3);
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {

    }
}