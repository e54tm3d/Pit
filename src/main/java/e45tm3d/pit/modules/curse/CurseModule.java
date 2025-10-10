package e45tm3d.pit.modules.curse;

import e45tm3d.pit.api.User;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.maps.CurseMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class CurseModule {

    public abstract ItemStack getMenuItem();

    public abstract ItemStack getEquipedItem();

    public abstract String getIdentifier();

    public abstract int getPrice();

    public abstract List<String> getConsumeItems();

    public abstract List<String> getUnlockedCostFormat();

    public abstract List<String> getLockedCostFormat();

    public abstract void listen(Event event);

    public abstract void run(CurseModule task);

    public boolean isEquiped(Player p) {
        return User.getEquipCurse(p, "slot_1").equals(getIdentifier())
                || User.getEquipCurse(p, "slot_2").equals(getIdentifier())
                ||  User.getEquipCurse(p, "slot_3").equals(getIdentifier());
    }

    public void register() {
        run(this);
        Curses.registerCurse(this);
        CurseLists.curse.add(getIdentifier());
        CurseMaps.curse_menu_items.put(getIdentifier(), getMenuItem());
        CurseMaps.curse_items.put(getIdentifier(), getEquipedItem());
        CurseMaps.consume_items.put(getIdentifier(), getConsumeItems());
        CurseMaps.price.put(getIdentifier(), getPrice());
        CurseMaps.lock.put(getIdentifier(), getLockedCostFormat());
        CurseMaps.unlock.put(getIdentifier(), getUnlockedCostFormat());
    }
}