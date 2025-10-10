package e45tm3d.pit.modules.buff;

import e45tm3d.pit.api.User;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.maps.BuffMaps;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class BuffModule {

    public abstract ItemStack getMenuItem();

    public abstract ItemStack getEquipedItem();

    public abstract String getIdentifier();

    public abstract int getPrice();

    public abstract List<String> getConsumeItems();

    public abstract List<String> getUnlockedCostFormat();

    public abstract List<String> getLockedCostFormat();

    public abstract void listen(Event event);

    public abstract void run(BuffModule task);

    public boolean isEquiped(Player p) {
        return User.getEquipBuff(p, "slot_1").equals(getIdentifier())
                || User.getEquipBuff(p, "slot_2").equals(getIdentifier())
                ||  User.getEquipBuff(p, "slot_3").equals(getIdentifier())
                ||  User.getEquipBuff(p, "slot_4").equals(getIdentifier())
                ||  User.getEquipBuff(p, "slot_5").equals(getIdentifier());
    }

    public void register() {
        run(this);
        Buffs.registerBuff(this);
        BuffLists.buff.add(getIdentifier());
        BuffMaps.buff_menu_items.put(getIdentifier(), getMenuItem());
        BuffMaps.buff_items.put(getIdentifier(), getEquipedItem());
        BuffMaps.consume_items.put(getIdentifier(), getConsumeItems());
        BuffMaps.price.put(getIdentifier(), getPrice());
        BuffMaps.lock.put(getIdentifier(), getLockedCostFormat());
        BuffMaps.unlock.put(getIdentifier(), getUnlockedCostFormat());
    }
}