package e45tm3d.pit.modules.buff.buffs;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.buff.BuffModule;
import e45tm3d.pit.utils.enums.buff.BuffItems;
import e45tm3d.pit.utils.enums.buff.BuffMenuItems;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Regeneration extends BuffModule {

    @Override
    public ItemStack getMenuItem() {
        return BuffMenuItems.REGENERATION.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return BuffItems.REGENERATION.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "regeneration";
    }

    @Override
    public int getPrice() {
        return Yaml.BUFF.getConfig().getInt("menu.items.regeneration.price", 100000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.BUFF.getConfig().getStringList("menu.items.regeneration.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.regeneration.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.regeneration.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityRegainHealthEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (isEquiped(p)) {
                    e.setAmount(e.getAmount() + 1);
                }
            }
        }
    }

    @Override
    public void run(BuffModule task) {

    }
}
