package e45tm3d.pit.modules.monsters.bosses;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.monsters.MonsterModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.functions.NMSFunction;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import e45tm3d.pit.utils.nms.Bossbar;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class SlimeKing extends MonsterModule {

    Bossbar boss = NMSFunction.newBossbar("");

    @Override
    public boolean isBoss() {
        return true;
    }

    @Override
    public String getName() {
        return "Slime King";
    }

    @Override
    public String getIdentifier() {
        return "slime_king";
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SLIME;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof SlimeSplitEvent e) {
            if (e.getEntity().hasMetadata("NO_SPLIT") || isType(e.getEntity())) {
                e.setCancelled(true);
            }

        } else if (event instanceof EntityDeathEvent e) {
            if (isType(e.getEntity())) {
                Location loc = e.getEntity().getLocation();
                for (int i = 0; i < MathFunction.randomDouble(15, 8); i++) {

                    Random r = new Random();

                    Item item = loc.getWorld().dropItem(loc, new ItemStack(Material.FIREBALL));

                    item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), String.valueOf(UUID.randomUUID())));
                    item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), "drop_promethean_fire"));

                    Vector v = new Vector(r.nextDouble() * 0.5 - 0.4, 0.42, r.nextDouble() * 0.5 - 0.4);
                    item.setPickupDelay(40);
                    item.setVelocity(v);
                }

                for (int i = 0; i <  MathFunction.randomDouble(25, 16); i++) {

                    Random r = new Random();

                    Item item = loc.getWorld().dropItem(loc, new ItemStack(Material.GOLD_NUGGET));
                    item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), String.valueOf(UUID.randomUUID())));

                    Vector v = new Vector(r.nextDouble() * 0.5 - 0.4, 0.42, r.nextDouble() * 0.5 - 0.4);
                    item.setPickupDelay(40);
                    item.setVelocity(v);
                }

                for (int i = 0; i <  MathFunction.randomDouble(5, 3); i++) {

                    Random r = new Random();

                    Item item = loc.getWorld().dropItem(loc, new ItemStack(Material.SLIME_BALL));

                    item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), String.valueOf(UUID.randomUUID())));
                    item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), "drop_slime_ball"));

                    Vector v = new Vector(r.nextDouble() * 0.5 - 0.4, 0.42, r.nextDouble() * 0.5 - 0.4);
                    item.setPickupDelay(40);
                    item.setVelocity(v);
                }
            }

        } else if (event instanceof PlayerPickupItemEvent e) {
            if (ItemFunction.hasNBTTag(e.getItem().getItemStack(), "drop_promethean_fire")) {
                e.setCancelled(true);
                e.getPlayer().getInventory().addItem(ItemFunction.searchItem("promethean_fire"));
                e.getItem().remove();
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 1);
            } else if (ItemFunction.hasNBTTag(e.getItem().getItemStack(), "drop_slime_ball")) {
                e.setCancelled(true);
                e.getPlayer().getInventory().addItem(ItemFunction.searchItem("slime_ball"));
                e.getItem().remove();
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 1);
            }

        } else if (event instanceof EntityDamageEvent e) {
            if (isType(e.getEntity())) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.FALL)) {
                    e.setCancelled(true);
                    for (Entity entity : e.getEntity().getNearbyEntities(30, 30, 30)) {
                        if (entity instanceof Player p && !PlayerFunction.isInSpawn(p)) {
                            if (p.getLocation().add(0, -0.05, 0).getBlock().getType().isSolid()) {
                                p.damage(10);

                                Vector vec = new Vector(0, 0.3, 0);

                                p.setVelocity(vec);
                            }
                        }
                    }
                    for (int i = 0; i < MathFunction.randomDouble(8, 5); i++) {
                        Random r = new Random();

                        double offsetX = r.nextDouble() * 5 - 4;
                        double offsetZ = r.nextDouble() * 5 - 4;

                        Location loc = e.getEntity().getLocation().add(offsetX, 0, offsetZ);
                        Entity slime = loc.getWorld().spawnEntity(loc, EntityType.SLIME);
                        slime.setMetadata("NO_SPLIT", new FixedMetadataValue(ThePit.getInstance(), true));
                        MonsterLists.entities.add(slime);
                    }
                }
            }
        }
    }

    @Override
    public void run(MonsterModule task) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!Bukkit.getOnlinePlayers().isEmpty() && getMonsters().isEmpty()) {
                removeAll();
                for (Location spawns : getMonsterSpawns()) {
                    Slime slime = (Slime) spawnEntity(spawns);
                    slime.setSize(12);
                    slime.setMaxHealth(2000);
                    slime.setHealth(2000);
                    slime.setCanPickupItems(false);
                    slime.setRemoveWhenFarAway(false);
                }
            }
        }, 0, MathFunction.time(0, 30, 0));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            if (!getMonsters().isEmpty()) {
                for (Entity entity : getMonsters()) {
                    if (entity instanceof Slime slime) {
                        if (!slime.isDead() && isType(slime)) {
                            for (Entity entities : slime.getNearbyEntities(30, 30, 30)) {
                                if (entities instanceof Player) {
                                    Vector vec = new Vector(0, 1.5, 0);
                                    slime.setVelocity(vec);
                                }
                            }
                        }
                    }
                }
            }
        }, 0, MathFunction.time(0, 0, 20));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!getMonsters().isEmpty()) {
                    for (Entity entity : getMonsters()) {
                        if (entity instanceof Slime slime) {
                            if (!slime.isDead() && isType(slime)) {
                                boss.setProgress(slime.getHealth() / slime.getMaxHealth());

                                String format = ChatColor.translateAlternateColorCodes('&', Yaml.CONFIG.getConfig().getString("settings.bossbar.title_format")
                                        .replace("%health%", Math.round(slime.getHealth()) + "")
                                        .replace("%max_health%", Math.round(slime.getMaxHealth()) + "")
                                        .replace("%name%", getName()));

                                boss.setTitle(format);
                                if (p.getLocation().distance(slime.getLocation()) <= 50) {
                                    boss.addPlayer(p);
                                } else {
                                    boss.removePlayer(p);
                                }
                            } else {
                                boss.removePlayer(p);
                            }
                        }
                    }
                } else {
                    boss.removePlayer(p);
                }
            }
        }, 0, MathFunction.time(0, 0, 1));
    }
}