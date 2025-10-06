package e45tm3d.pit.modules.monsters.monsters;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

import java.util.Random;

public class Slime extends MonsterModule {

    final int MAX_COUNT = getMonsterSpawns().size();

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public String getName() {
        return "Slime";
    }

    @Override
    public String getType() {
        return "slime";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SLIME;
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
                if (r.nextInt(100) <= 10) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("promethean_fire"));
                }
                if (r.nextInt(100) <= 5) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("slime_ball"));
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsterSpawns().isEmpty() && getMonsters().size() < MAX_COUNT) {
                org.bukkit.entity.Slime slime = (org.bukkit.entity.Slime) spawnEntity(getMonsterSpawns().get(new Random().nextInt(MAX_COUNT)));
                slime.setSize(3);
                slime.setMaxHealth(20);
                slime.setHealth(20);
            }
        }, 0, MathFunction.time(0, 0, 45));
    }
}
