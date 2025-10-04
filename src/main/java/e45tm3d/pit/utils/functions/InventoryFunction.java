package e45tm3d.pit.utils.functions;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryFunction {

    public static boolean hasInventorySpace(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item == null || item.getType() == Material.AIR) {
                return true;
            }
        }
        return false;
    }

}
