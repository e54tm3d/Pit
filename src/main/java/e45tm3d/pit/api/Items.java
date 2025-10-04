package e45tm3d.pit.api;

import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Items {

    public static ItemStack searchItem(String item) {
        return ItemFunction.searchItem(item);
    }

    public static boolean isItem(ItemStack item, String itemName) {
        return ItemFunction.isItem(item, itemName);
    }

    public static void consumeItem(Player p, int amount, String item) {
        ItemFunction.consumeItem(p, amount, item);
    }

    public static boolean hasItemAtLease(Player p, int amount, String item) {
        return ItemFunction.hasItemAtLease(p, amount, item);
    }

}
