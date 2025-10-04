package e45tm3d.pit.modules.monsters.monsters;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class Zombie extends MonsterModule {

    final int MAX_COUNT = getMonsterSpawns().size();

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public String getType() {
        return "zombie";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.ZOMBIE;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDeathEvent e) {
            if (isType(e.getEntity())) {
                Random r = new Random();
                if (r.nextInt(100) <= 2) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("promethean_fire"));
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsterSpawns().isEmpty() && getMonsters().size() < MAX_COUNT) {
                org.bukkit.entity.Zombie zombie = (org.bukkit.entity.Zombie) spawnEntity(getMonsterSpawns().get(new Random().nextInt(MAX_COUNT)));
                zombie.setMaxHealth(20);
                zombie.setHealth(20);
                zombie.setTicksLived(600);
                zombie.setBaby(false);
                zombie.setCanPickupItems(false);
                zombie.setVillager(false);
                zombie.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                zombie.getEquipment().setHelmetDropChance(0);
                zombie.getEquipment().setItemInHandDropChance(0);
                zombie.setRemoveWhenFarAway(true);
            }
        }, 0, MathFunction.time(0, 1, 0));
    }
}
