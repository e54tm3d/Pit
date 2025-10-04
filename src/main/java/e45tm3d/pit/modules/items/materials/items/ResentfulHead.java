package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class ResentfulHead extends MaterialModule {

    @Override
    public String getType() {
        return "resentful_head";
    }

    @Override
    public ItemStack getItem() {
        return Materials.RESENTFUL_HEAD.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerInteractEvent e) {
            if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if (isItem(e.getItem())) {
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof PlayerDeadEvent e) {

            Player p = e.getDead();

            Random r = new Random();
            if (r.nextInt(100) < 10) {
                p.getInventory().addItem(ItemFunction.searchItem(getType()));
            }
        }
    }

    @Override
    public void run(MaterialModule task) {

    }
}
