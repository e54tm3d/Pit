package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class Bone extends MaterialModule {

    @Override
    public String getType() {
        return "bone";
    }

    @Override
    public ItemStack getItem() {
        return Materials.BONE.getItemStack();
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(MaterialModule task) {

    }
}
