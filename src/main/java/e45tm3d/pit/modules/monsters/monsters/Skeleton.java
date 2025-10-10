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
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Skeleton extends MonsterModule {

    @Override
    public boolean isBoss() {
        return false;
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

    @Override
    public String getIdentifier() {
        return "skeleton";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SKELETON;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDeathEvent e) {
            if (isType(e.getEntity())) {
                Random r = new Random();
                if (r.nextInt(100) <= 5) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("promethean_fire"));
                }
                if (r.nextInt(100) <= 5) {
                    Bukkit.getWorld(VariableFunction.getActiveArena()).dropItem(e.getEntity().getLocation(), ItemFunction.searchItem("bone"));
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {

            int MAX_COUNT = getMonsterSpawns().size();

            if (!Bukkit.getOnlinePlayers().isEmpty() && !getMonsterSpawns().isEmpty() && getMonsters().size() < MAX_COUNT) {
                org.bukkit.entity.Skeleton skeleton = (org.bukkit.entity.Skeleton) spawnEntity(getMonsterSpawns().get(new Random().nextInt(MAX_COUNT)));
                skeleton.setMaxHealth(20);
                skeleton.setHealth(20);
                skeleton.setTicksLived(600);
                skeleton.setCanPickupItems(false);
                skeleton.getEquipment().setHelmet(new ItemStack(Material.LEATHER_HELMET));
                skeleton.getEquipment().setItemInHand(new ItemStack(Material.IRON_SWORD));
                skeleton.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 0));
                skeleton.getEquipment().setHelmetDropChance(0);
                skeleton.getEquipment().setItemInHandDropChance(0);
                skeleton.setRemoveWhenFarAway(true);
            }
        }, 0, MathFunction.time(0, 1, 0));
    }
}
