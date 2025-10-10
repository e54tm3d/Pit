package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.api.listeners.LevelChangeEvent;
import e45tm3d.pit.api.events.PlayerPitLevelChangeEvent;
import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.Properties;
import java.util.Random;

public class LiquidExperience extends MaterialModule {

    @Override
    public String getIdentifier() {
        return "liquid_experience";
    }

    @Override
    public ItemStack getItem() {
        return Materials.LIQUID_EXPERIENCE.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerPitLevelChangeEvent e) {

            Random r = new Random();

            if (r.nextInt(100) <= 10) {
                ItemStack item = ItemFunction.searchItem(getIdentifier());
                e.getPlayer().getInventory().addItem(item);
            }
        } else if (event instanceof PlayerInteractEvent e) {
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
