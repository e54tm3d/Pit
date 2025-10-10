package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.MonsterFunction;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class RottenFlesh extends MaterialModule {
    @Override
    public String getIdentifier() {
        return "rotten_flesh";
    }

    @Override
    public ItemStack getItem() {
        return Materials.ROTTEN_FLESH.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerItemConsumeEvent e) {
            if (isItem(e.getItem())) {
                e.setCancelled(true);
            }
        }
    }

    @Override
    public void run(MaterialModule task) {

    }
}
