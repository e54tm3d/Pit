package e45tm3d.pit.utils.functions;

import e45tm3d.pit.utils.lists.ItemLists;
import e45tm3d.pit.utils.maps.MaterialMaps;
import e45tm3d.pit.utils.maps.WeaponMaps;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemFunction {

    public static ItemStack addAttribute(ItemStack item, String attributeName, double amount, int operation) {
        return NMSFunction.addAttribute(item, attributeName, amount, operation);
    }

    public static ItemStack addNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.addNBTTag(item, nbtTagName);
    }

    public static boolean hasNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.hasNBTTag(item, nbtTagName);
    }

    public static ItemStack removeNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.removeNBTTag(item, nbtTagName);
    }

    public static void spawnItemExplosion(Location location, org.bukkit.inventory.ItemStack item, int amount, int radius, int speed) {
        NMSFunction.spawnItemExplosion(location, item, amount, radius, speed);
    }

    public static void registerMaterial(String item, ItemStack itemStack) {
        ItemLists.materials.add(item.toLowerCase());
        MaterialMaps.material_items.put(item.toLowerCase(), itemStack);
    }

    public static List<String> searchWeapons() {
        return ItemLists.weapons;
    }

    public static List<String> searchMaterials() {
        return ItemLists.materials;
    }

    public static List<String> searchAllItems() {
        List<String> items = new ArrayList<>();
        items.addAll(ItemLists.materials);
        items.addAll(ItemLists.weapons);
        return items;
    }

    public static ItemStack searchMaterial(String str) {
        return MaterialMaps.material_items.get(str);
    }

    public static ItemStack searchWeapons(String str) {
        return WeaponMaps.weapon_items.get(str);
    }

    public static ItemStack searchItem(String item) {

        Map<String, ItemStack> allItems = MaterialMaps.material_items;
        allItems.putAll(WeaponMaps.weapon_items);
        allItems.putAll(MaterialMaps.material_items);

        return allItems.getOrDefault(item, null);
    }

    public static boolean hasItemAtLease(Player p, int amount, String item) {
        int i = 0;
        for (ItemStack items : p.getInventory().getContents()) {
            if (isItem(items, item)) {
                i += items.getAmount();
            }
        }
        return i >= amount;
    }

    public static void consumeItem(Player p, int amount, String item) {
        for (ItemStack items : p.getInventory().getContents()) {
            if (isItem(items, item)) {
                if (Math.max(items.getAmount() - amount, 0) > 0) {
                    items.setAmount(Math.max(items.getAmount() - amount, 0));
                } else {
                    p.getInventory().remove(items);
                }
                break;
            }
        }
    }

    public static boolean isItem(ItemStack items, String item) {
        return ItemFunction.hasNBTTag(items, item.toLowerCase());
    }
}
