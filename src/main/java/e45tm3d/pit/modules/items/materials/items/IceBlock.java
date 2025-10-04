package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class IceBlock extends MaterialModule {

    @Override
    public String getType() {
        return "ice_block";
    }

    @Override
    public ItemStack getItem() {
        return Materials.ICE_BLOCK.getItemStack();
    }

    @Override
    public void listen(Event event) {
    if (event instanceof PlayerMurderEvent e) {

        Player p = e.getDead();

        if (User.getLevel(p) >= 20) {
            Random r = new Random();
            if (r.nextInt(100) <= 20) {
                e.getDead().getWorld().dropItemNaturally(e.getDead().getLocation(), ItemFunction.searchItem(getType()));
            }
        }
    } else if (event instanceof BlockPlaceEvent e) {
            if (isItem(e.getItemInHand())) {
                e.setCancelled(true);
            }
        }
    }

    @Override
    public void run(MaterialModule task) {

    }
}
