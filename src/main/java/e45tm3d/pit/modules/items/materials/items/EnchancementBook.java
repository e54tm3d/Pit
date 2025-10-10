package e45tm3d.pit.modules.items.materials.items;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.materials.MaterialModule;
import e45tm3d.pit.utils.enums.materials.Materials;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.NMSFunction;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

public class EnchancementBook extends MaterialModule {
    @Override
    public String getIdentifier() {
        return "enchancement_book";
    }

    @Override
    public ItemStack getItem() {
        return Materials.ENCHANCEMENT_BOOK.getItemStack();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerMurderEvent e) {

            Player p = e.getDead();

            if (User.getLevel(p) >= 100) {
                Random r = new Random();
                if (r.nextInt(100) <= 5) {
                    e.getDead().getWorld().dropItemNaturally(e.getDead().getLocation(), ItemFunction.searchItem(getIdentifier()));
                }
            }
        }
    }

    @Override
    public void run(MaterialModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!Objects.isNull(p.getItemInHand())
                && !Objects.isNull(p.getItemInHand().getItemMeta())) {
                    if (isItem(p.getItemInHand())) {
                        Location loc = p.getLocation().add(0, 1, 0);
                        double offsetX = MathFunction.randomDouble(2, -2);
                        double offsetY = MathFunction.randomDouble(2, -2);
                        double offsetZ = MathFunction.randomDouble(2, -2);
                        loc = loc.add(offsetX, offsetY, offsetZ);
                        NMSFunction.spawnSingleParticle(EnumParticle.ENCHANTMENT_TABLE, loc, p);
                    }
                }
            }
        }, 0, 5);
    }
}
