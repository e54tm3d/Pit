package e45tm3d.pit.modules.buff;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.Curses;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.maps.BuffMaps;
import e45tm3d.pit.utils.maps.CurseMaps;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public abstract class BuffModule {

    public abstract ItemStack getMenuItem();

    public abstract ItemStack getEquipedItem();

    public abstract String getType();

    public abstract int getPrice();

    public abstract List<String> getConsumeItems();

    public abstract List<String> getUnlockedCostFormat();

    public abstract List<String> getLockedCostFormat();

    public abstract void listen(Event event);

    public abstract void run(BuffModule task);

    public boolean isEquiped(Player p) {
        return User.getEquipBuff(p, "slot_1").equals(getType())
                || User.getEquipBuff(p, "slot_2").equals(getType())
                ||  User.getEquipBuff(p, "slot_3").equals(getType())
                ||  User.getEquipBuff(p, "slot_4").equals(getType())
                ||  User.getEquipBuff(p, "slot_5").equals(getType());
    }

    public void register() {
        run(this);
        Buffs.registerBuff(this);
        BuffLists.buff.add(getType());
        BuffMaps.buff_menu_items.put(getType(), getMenuItem());
        BuffMaps.buff_items.put(getType(), getEquipedItem());
        BuffMaps.consume_items.put(getType(), getConsumeItems());
        BuffMaps.price.put(getType(), getPrice());
        BuffMaps.lock.put(getType(), getLockedCostFormat());
        BuffMaps.unlock.put(getType(), getUnlockedCostFormat());
    }
}