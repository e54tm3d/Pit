package e45tm3d.pit.modules.curse.curses;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.CurseModule;
import e45tm3d.pit.utils.enums.curse.CurseItems;
import e45tm3d.pit.utils.enums.curse.CurseMenuItems;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import net.minecraft.server.v1_8_R3.EntityPositionTypes;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BloodyCurse extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.BLOODYCURSE.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.BLOODYCURSE.getItemStack();
    }

    @Override
    public String getType() {
        return "bloody_curse";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.bloody_curse.price", 120000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.bloody_curse.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloody_curse.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloody_curse.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityRegainHealthEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (isEquiped(p)) {
                    if (!VariableFunction.isInSpawn(p.getLocation())) {
                        e.setCancelled(true);
                    }
                }
            }
        } else if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (isEquiped(p)) {
                    if (!VariableFunction.isInSpawn(p.getLocation())) {
                        e.setDamage(e.getDamage() * 1.5);
                    }
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {

    }
}
