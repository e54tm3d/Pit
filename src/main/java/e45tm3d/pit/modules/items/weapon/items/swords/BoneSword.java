package e45tm3d.pit.modules.items.weapon.items.swords;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.EntityBoneBrokenEvent;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class BoneSword extends WeaponModule {

    private final Map<UUID, Long> bone_broken = new HashMap<>();

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.bone_sword.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.bone_sword.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.bone_sword.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.bone_sword.tier_4.price");
            }
            default -> {
                return 0;
            }
        }
    }

    @Override
    public String getTierName(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_4.name");
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    public List<String> getTierConsumeItems(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_4.consume_items");
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }

    @Override
    public List<String> getTierLore(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.bone_sword.tier_4.lore");
            }
            default -> {
                return Collections.emptyList();
            }
        }
    }

    @Override
    public String getTierUpgradeCostFormat(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.bone_sword.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getType() {
        return "bone_sword";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.BONE_SWORD.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.BONE_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 15;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {

                if (usingItem(p)) {

                    Random r = new Random();

                    if (User.getWeaponLevel(p, getType()) >= 3) {
                        if (r.nextInt(100) < 10) {
                            Bukkit.getPluginManager().callEvent(new EntityBoneBrokenEvent(e.getEntity(), e.getDamager()));
                            bone_broken.put(e.getEntity().getUniqueId(), System.currentTimeMillis());


                            for (int i = 0; i < 10; i++) {
                                Item item = e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), new ItemStack(Material.BONE));
                                item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), String.valueOf(UUID.randomUUID())));
                                item.setMetadata("FAKE_BONE", new FixedMetadataValue(ThePit.getInstance(), true));
                                org.bukkit.util.Vector v = new Vector(r.nextDouble() * 0.2 - 0.1,
                                        0.42,
                                        r.nextDouble() * 0.2 - 0.1);
                                item.setPickupDelay(100);
                                item.setTicksLived(40);
                                item.setVelocity(v);
                                Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), item::remove, 40);
                            }
                            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.SKELETON_DEATH, 1, 1);
                        }
                    }

                    if (User.getWeaponLevel(p, getType()) >= 2) {
                        e.setDamage(e.getDamage() + 2);
                    }
                }
            }
        } else if (event instanceof PlayerPickupItemEvent e) {
            if (e.getItem().hasMetadata("FAKE_BONE")) {
                e.setCancelled(true);
                e.getItem().remove();
            }
        }
    }

    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {

            long currentTime = System.currentTimeMillis();
            bone_broken.entrySet().removeIf(entry -> currentTime - entry.getValue() > 5000);

            for (Entity entity : Bukkit.getWorld(VariableFunction.getActiveArena()).getEntities()) {
                if (entity instanceof LivingEntity le) {
                    if (System.currentTimeMillis() - bone_broken.getOrDefault(le.getUniqueId(), 0L) < 5000) {
                        le.damage(1);
                    }
                }
            }
        }, 20, 20);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (usingItem(p)) {
                    if (User.getWeaponLevel(p, getType()) >= 4)
                        p.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 1), true);
                }
            }
        }, 60, 60);
    }
}
