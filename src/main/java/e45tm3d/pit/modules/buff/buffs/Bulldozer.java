package e45tm3d.pit.modules.buff.buffs;

import com.google.common.collect.Lists;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.buff.BuffModule;
import e45tm3d.pit.utils.enums.buff.BuffItems;
import e45tm3d.pit.utils.enums.buff.BuffMenuItems;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class Bulldozer extends BuffModule {

    @Override
    public ItemStack getMenuItem() {
        return BuffMenuItems.BULLDOZER.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return BuffItems.BULLDOZER.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "bulldozer";
    }

    @Override
    public int getPrice() {
        return Yaml.BUFF.getConfig().getInt("menu.items.bulldozer.price", 10000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.BUFF.getConfig().getStringList("menu.items.bulldozer.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.bulldozer.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.bulldozer.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerMurderEvent e) {
            if (e.getKiller() instanceof Player p) {
                if (isEquiped(p)) {
                    p.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(100, 0));
                }
            }
        }
    }

    @Override
    public void run(BuffModule task) {

    }
}
