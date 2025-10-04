package e45tm3d.pit.modules.curse;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.maps.CurseMaps;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public abstract class CurseModule {

    public abstract ItemStack getMenuItem();

    public abstract ItemStack getEquipedItem();

    public abstract String getType();

    public abstract int getPrice();

    public abstract List<String> getConsumeItems();

    public abstract List<String> getUnlockedCostFormat();

    public abstract List<String> getLockedCostFormat();

    public abstract void listen(Event event);

    public abstract void run(CurseModule task);

    public boolean isEquiped(Player p) {
        return User.getEquipCurse(p, "slot_1").equals(getType())
                || User.getEquipCurse(p, "slot_2").equals(getType())
                ||  User.getEquipCurse(p, "slot_3").equals(getType());
    }

    public void register() {
        run(this);
        Curses.registerCurse(this);
        CurseLists.curse.add(getType());
        CurseMaps.curse_menu_items.put(getType(), getMenuItem());
        CurseMaps.curse_items.put(getType(), getEquipedItem());
        CurseMaps.consume_items.put(getType(), getConsumeItems());
        CurseMaps.price.put(getType(), getPrice());
        CurseMaps.lock.put(getType(), getLockedCostFormat());
        CurseMaps.unlock.put(getType(), getUnlockedCostFormat());
    }
}