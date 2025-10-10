package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

public class SlimeBall extends MaterialModule {
    @Override
    public String getIdentifier() {
        return "slime_ball";
    }

    @Override
    public ItemStack getItem() {
        return Materials.SLIME_BALL.getItemStack();
    }

    @Override
    public void listen(Event event) {

    }

    @Override
    public void run(MaterialModule task) {

    }
}
