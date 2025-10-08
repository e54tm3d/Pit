package e45tm3d.pit.modules.items.weapon;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import e45tm3d.pit.utils.lists.ItemLists;
import e45tm3d.pit.utils.maps.WeaponMaps;
import e45tm3d.pit.utils.menus.WeaponMenu;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public abstract class WeaponModule {

    public abstract String getType();

    public abstract ItemStack getMenuItem();

    public abstract ItemStack getItem();

    public abstract int getSlot();

    public abstract void listen(Event event);

    public abstract void run(WeaponModule task);

    public abstract int getTierPrice(int tier);

    public abstract String getTierName(int tier);

    public abstract List<String> getTierConsumeItems(int tier);

    public abstract List<String> getTierLore(int tier);

    public abstract String getTierUpgradeCostFormat(int tier);

    public abstract String getTierLevelmaxCostFormat(int tier);

    public boolean usingItem(Player p) {
        if (!p.getItemInHand().hasItemMeta()) return false;
        if (p.getItemInHand().getItemMeta() == null) return false;
        return ItemFunction.hasNBTTag(p.getItemInHand(), getType());
    }

    public boolean containItemAtLeast(Player p, int amount) {

        boolean b = false;
        int count = 0;

        for (ItemStack item : p.getInventory().getContents()) {
            if (ItemFunction.hasNBTTag(item, getType())) {
                count += item.getAmount();
                if (count >= amount) {
                    b = true;
                    break;
                }
            }
        }
        return b;
    }

    public boolean hasItem(Player p) {

        boolean b = false;

        for (ItemStack item : p.getInventory().getContents()) {
            if (ItemFunction.hasNBTTag(item, getType())) {
                b = true;
                break;
            }
        }
        return b;
    }

    public void register() {

        ItemStack item = getItem();
        item = ItemFunction.addNBTTag(item, getType());
        ItemMeta meta = item.getItemMeta();
        item.setItemMeta(meta);

        if (getSlot() >= 0 && getSlot() <= 45) {

            for (int i = 1; i <= 4; i++) {
                WeaponMaps.setTierPrice(getType(), i, getTierPrice(i));
                WeaponMaps.setTierConsumeItem(getType(), i, getTierConsumeItems(i));
                WeaponMaps.setTierName(getType(), i, getTierName(i));
                WeaponMaps.setTierLore(getType(), i, getTierLore(i));

                WeaponMaps.setTierUpgradeCostFormat(getType(), i, VariableFunction.removeBrackets(getTierUpgradeCostFormat(i)));
                WeaponMaps.setTierLevelmaxCostFormat(getType(), i, VariableFunction.removeBrackets(getTierLevelmaxCostFormat(i)));
            }

            WeaponMenu.getInventory().setItem(getSlot(), getMenuItem());
            WeaponMaps.weapon_slots.put(getType(), getSlot());
            WeaponMaps.weapon_items.put(getType(), item);
            ItemLists.weapons.add(getType());

            run(this);

            Weapons.registerWeapon(this);
        } else {
            ThePit.getInstance().getLogger().warning("Weapon slot is out of range (0 ~ 44)!");
        }
    }
}