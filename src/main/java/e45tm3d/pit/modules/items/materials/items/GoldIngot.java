package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class GoldIngot extends MaterialModule {

    @Override
    public String getType() {
        return "gold_ingot";
    }

    @Override
    public ItemStack getItem() {
        return Materials.GOLD_INGOT.getItemStack();
    }

    @Override
    public void listen(Event event) {
    }

    @Override
    public void run(MaterialModule task) {
    }
}
