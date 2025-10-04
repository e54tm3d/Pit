package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class Superconductor extends MaterialModule {

    @Override
    public String getType() {
        return "superconductor";
    }

    @Override
    public ItemStack getItem() {
        return Materials.SUPERCONDUCTOR.getItemStack();
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(MaterialModule task) {

    }
}
