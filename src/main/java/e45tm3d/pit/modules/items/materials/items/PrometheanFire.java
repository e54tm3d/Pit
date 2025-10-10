package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PrometheanFire extends MaterialModule {

    @Override
    public String getIdentifier() {
        return "promethean_fire";
    }

    @Override
    public ItemStack getItem() {
        return Materials.PROMETHEAN_FIRE.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerInteractEvent e) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (isItem(e.getItem())) {
                    e.setCancelled(true);
                }
            }
        }
    }

    @Override
    public void run(MaterialModule task) {

    }
}
