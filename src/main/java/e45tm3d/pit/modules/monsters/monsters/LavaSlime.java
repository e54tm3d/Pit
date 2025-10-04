package e45tm3d.pit.modules.monsters.monsters;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MagmaCube;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.Random;

public class LavaSlime extends MonsterModule {

    final int MAX_COUNT = getMonsterSpawns().size();

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public String getName() {
        return "Lava Slime";
    }

    @Override
    public String getType() {
        return "lava_slime";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.MAGMA_CUBE;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof SlimeSplitEvent e) {
            if (isType(e.getEntity())) {
                e.setCancelled(true);
            }
        } else if (event instanceof EntityDeathEvent e) {
            if (isType(e.getEntity())) {
                Random r = new Random();
                if (r.nextInt(100) < 40) {
                    e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("promethean_fire"));
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsterSpawns().isEmpty() && getMonsters().size() < MAX_COUNT) {
                MagmaCube lava = (MagmaCube) spawnEntity(getMonsterSpawns().get(new Random().nextInt(MAX_COUNT)));
                lava.setSize(4);
                lava.setMaxHealth(60);
                lava.setHealth(60);
                lava.setTicksLived(600);
                lava.setRemoveWhenFarAway(true);
            }
        }, 0, MathFunction.time(0, 1, 0));
    }
}