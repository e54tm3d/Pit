package e45tm3d.pit.modules.curse.curses;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.curse.CurseModule;
import e45tm3d.pit.utils.enums.curse.CurseItems;
import e45tm3d.pit.utils.enums.curse.CurseMenuItems;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BloodyFire extends CurseModule {

    @Override
    public ItemStack getMenuItem() {
        return CurseMenuItems.BLOODYFIRE.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return CurseItems.BLOODYFIRE.getItemStack();
    }

    @Override
    public String getType() {
        return "bloodyfire";
    }

    @Override
    public int getPrice() {
        return Yaml.CURSE.getConfig().getInt("menu.items.bloodyfire.price", 150000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.CURSE.getConfig().getStringList("menu.items.bloodyfire.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloodyfire.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.CURSE.getConfig().getStringList("menu.items.bloodyfire.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {
                if (isEquiped(p) && !e.isCancelled()) {
                    p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 0.5));
                }
            } else if (e.getDamager() instanceof Projectile pro) {
                if (pro.getShooter() instanceof Player p) {
                    if (isEquiped(p) && !e.isCancelled() && e.getDamage() > 0) {
                        p.setHealth(Math.min(p.getMaxHealth(), p.getHealth() + 0.5));
                    }
                }
            }
        }
    }

    @Override
    public void run(CurseModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (isEquiped(p)) {
                    if (!PlayerFunction.isInSpawn(p)) {
                        p.setHealth(Math.max(0.5, p.getHealth() - 0.5));
                    }
                    for (int i = 0; i < 5; i++) {
                        double offsetX = MathFunction.randomDouble(1, -1);
                        double offsetY = MathFunction.randomDouble(1, -1);
                        double offsetZ = MathFunction.randomDouble(1, -1);
                        PlayerFunction.spawnSingleParticleToAll(EnumParticle.FLAME, p.getLocation().add(offsetX, offsetY + 1, offsetZ));
                    }
                }
            }
        }, 20, 20);
    }
}
