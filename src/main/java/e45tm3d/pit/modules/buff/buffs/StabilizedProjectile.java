package e45tm3d.pit.modules.buff.buffs;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.buff.BuffModule;
import e45tm3d.pit.utils.enums.buff.BuffItems;
import e45tm3d.pit.utils.enums.buff.BuffMenuItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class StabilizedProjectile extends BuffModule {

    @Override
    public ItemStack getMenuItem() {
        return BuffMenuItems.STABILIZED_PROJECTILE.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return BuffItems.STABILIZED_PROJECTILE.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "stabilized_projectile";
    }

    @Override
    public int getPrice() {
        return Yaml.BUFF.getConfig().getInt("menu.items.stabilized_projectile.price", 100000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.BUFF.getConfig().getStringList("menu.items.stabilized_projectile.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.stabilized_projectile.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.stabilized_projectile.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
    }

    @Override
    public void run(BuffModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Projectile projectile : Bukkit.getWorlds().get(0).getEntitiesByClass(Projectile.class)) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    boolean close = projectile.getLocation().distance(player.getLocation().add(0, 1, 0)) < 3;
                    if (close && !projectile.getShooter().equals(player)) {

                        double acceleration = 0.15;

                        Vector vec = new Vector(
                                Math.max(0, projectile.getVelocity().getX() - acceleration)
                                , Math.max(0, projectile.getVelocity().getY() - acceleration)
                                , Math.max(0, projectile.getVelocity().getZ() - acceleration));
                        projectile.setVelocity(vec);
                    }
                }
            }
        }, 1, 1);
    }
}
