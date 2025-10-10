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

import java.util.Random;

public class LightningCreeper extends MonsterModule {

    final int MAX_COUNT = getMonsterSpawns().size();

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public String getName() {
        return "Lightning Creeper";
    }

    @Override
    public String getIdentifier() {
        return "lightning_creeper";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.CREEPER;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDeathEvent e) {
            if (isType(e.getEntity())) {
                Random r = new Random();
                if (r.nextInt(100) <= 20) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("promethean_fire"));
                }
                if (r.nextInt(100) <= 25) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("superconductor"));
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsterSpawns().isEmpty() && getMonsters().size() < MAX_COUNT) {
                    org.bukkit.entity.Creeper creeper = (org.bukkit.entity.Creeper) spawnEntity(getMonsterSpawns().get(new Random().nextInt(MAX_COUNT)));
                    creeper.setPowered(true);
                    creeper.setMaxHealth(40);
                    creeper.setHealth(40);
            }
        }, 0, MathFunction.time(0, 2, 0));
    }
}
