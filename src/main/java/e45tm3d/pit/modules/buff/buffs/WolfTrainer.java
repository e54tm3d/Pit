package e45tm3d.pit.modules.buff.buffs;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.buff.BuffModule;
import e45tm3d.pit.utils.enums.buff.BuffItems;
import e45tm3d.pit.utils.enums.buff.BuffMenuItems;
import e45tm3d.pit.utils.functions.MathFunction;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class WolfTrainer extends BuffModule {

    @Override
    public ItemStack getMenuItem() {
        return BuffMenuItems.WOLF_TRAINER.getItemStack();
    }

    @Override
    public ItemStack getEquipedItem() {
        return BuffItems.WOLF_TRAINER.getItemStack();
    }

    @Override
    public String getIdentifier() {
        return "wolf_trainer";
    }

    @Override
    public int getPrice() {
        return Yaml.BUFF.getConfig().getInt("menu.items.wolf_trainer.price", 80000);
    }

    @Override
    public List<String> getConsumeItems() {
        return Yaml.BUFF.getConfig().getStringList("menu.items.wolf_trainer.consume_items");
    }

    @Override
    public List<String> getUnlockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.wolf_trainer.cost_format.unlock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public List<String> getLockedCostFormat() {
        List<String> list = Lists.newArrayList();
        for (String str : Yaml.BUFF.getConfig().getStringList("menu.items.wolf_trainer.cost_format.lock")) {
            list.add(str.replaceAll("&", "§"));
        }
        return list;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerJoinEvent e) {

            Player p = e.getPlayer();

            if (isEquiped(p)) {
                for (Entity entity : p.getWorld().getEntities()) {
                    if (entity instanceof Wolf wolf) {
                        if (wolf.hasMetadata("wolf_trainer") && wolf.getOwner().equals(p)) {
                            wolf.remove();
                        }
                    }
                }
                for (int i = 0; i < 3; i++) {

                    double offsetX = MathFunction.randomDouble(3, -3);
                    double offsetZ = MathFunction.randomDouble(3, -3);

                    Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(offsetX, 0, offsetZ), EntityType.WOLF);
                    wolf.setOwner(p);
                    wolf.setAdult();
                    wolf.setMaxHealth(20);
                    wolf.setHealth(20);
                    wolf.setCanPickupItems(false);
                    wolf.setAgeLock(true);
                    wolf.setCollarColor(DyeColor.WHITE);
                    wolf.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1), false);
                    wolf.setTicksLived(1200);
                    wolf.setMetadata("wolf_trainer", new FixedMetadataValue(ThePit.getInstance(), true));
                }
            }
        } else if (event instanceof PlayerQuitEvent e) {
            Player p = e.getPlayer();
            if (isEquiped(p)) {
                for (Entity entity : p.getWorld().getEntities()) {
                    if (entity instanceof Wolf wolf) {
                        if (wolf.getOwner().equals(p) && wolf.hasMetadata("wolf_trainer")) {
                            wolf.remove();
                        }
                    }
                }
            }
        } else if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getEntity() instanceof Wolf wolf) {
                if (e.getDamager() instanceof Projectile projectile) {
                    if (projectile.getShooter().equals(wolf.getOwner()) && wolf.hasMetadata("wolf_trainer")) {
                        e.setCancelled(true);
                    }
                }
                if (wolf.getOwner().equals(e.getDamager()) && wolf.hasMetadata("wolf_trainer")) {
                    e.setDamage(0);
                    e.setCancelled(true);
                }
            }
        } else if (event instanceof EntityDamageEvent e) {
            if (e.getEntity() instanceof Wolf wolf) {
                if (wolf.hasMetadata("wolf_trainer")) {
                    if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)
                            || e.getCause().equals(EntityDamageEvent.DamageCause.FALLING_BLOCK)) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @Override
    public void run(BuffModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (isEquiped(p)) {
                    for (Entity entity : p.getWorld().getEntities()) {
                        if (entity instanceof Wolf wolf) {
                            if (wolf.hasMetadata("wolf_trainer") && wolf.getOwner().equals(p)) {
                                if (wolf.getLocation().distance(p.getLocation()) > 10) {
                                    wolf.teleport(p.getLocation());
                                }
                            }
                        }
                    }
                }
            }
        }, 100, 100);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (isEquiped(p)) {
                    for (Entity entity : p.getWorld().getEntities()) {
                        if (entity instanceof Wolf wolf) {
                            if (wolf.hasMetadata("wolf_trainer") && wolf.getOwner().equals(p)) {
                                wolf.remove();
                            }
                        }
                    }
                    for (int i = 0; i < 3; i++) {

                        double offsetX = MathFunction.randomDouble(3, -3);
                        double offsetZ = MathFunction.randomDouble(3, -3);

                        Wolf wolf = (Wolf) p.getWorld().spawnEntity(p.getLocation().add(offsetX, 0, offsetZ), EntityType.WOLF);
                        wolf.setOwner(p);
                        wolf.setAdult();
                        wolf.setMaxHealth(20);
                        wolf.setHealth(20);
                        wolf.setCanPickupItems(false);
                        wolf.setAgeLock(true);
                        wolf.setCollarColor(DyeColor.WHITE);
                        wolf.addPotionEffect(PotionEffectType.SPEED.createEffect(Integer.MAX_VALUE, 1), false);
                        wolf.setTicksLived(1200);
                        wolf.setMetadata("wolf_trainer", new FixedMetadataValue(ThePit.getInstance(), true));
                    }
                }
            }
        }, 1200, 1200);
    }
}
