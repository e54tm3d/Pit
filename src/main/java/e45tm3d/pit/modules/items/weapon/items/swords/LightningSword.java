package e45tm3d.pit.modules.items.weapon.items.swords;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.EntityLightningEvent;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LightningSword extends WeaponModule {

    private Map<UUID, Long> lightning = new HashMap<>();

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.lightning_sword.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.lightning_sword.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.lightning_sword.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.lightning_sword.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.lightning_sword.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.lightning_sword.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getType() {
        return "lightning_sword";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.LIGHTNING_SWORD.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.LIGHTNING_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 14;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player damager) {
                if (usingItem(damager)) {

                    if (User.getWeaponLevel(damager, getType()) < 1) {
                        Messages.WEAPON_LOCKED.sendMessage(damager).cooldown(5000);
                        e.setDamage(1);
                    }

                    if (e.getEntity() instanceof LivingEntity entity) {
                        UUID uuid = entity.getUniqueId();
                        if (User.getWeaponLevel(damager, getType()) >= 2) {
                            Random r = new Random();
                            if (r.nextInt(100) < 10) {
                                Bukkit.getPluginManager().callEvent(new EntityLightningEvent(entity));
                                damager.getWorld().strikeLightningEffect(entity.getLocation());
                                lightning.put(uuid, System.currentTimeMillis());
                                entity.setNoDamageTicks(0);
                                entity.damage(e.getFinalDamage() * 0.5);
                                entity.setNoDamageTicks(20);
                            }
                        }
                        if (lightning.containsKey(uuid)) {
                            if (System.currentTimeMillis() - lightning.get(entity.getUniqueId()) <= 3000) {
                                if (User.getWeaponLevel(damager, getType()) >= 3) {
                                    e.setDamage(e.getDamage() * 1.5);
                                }
                                if (User.getWeaponLevel(damager, getType()) >= 4) {
                                    for (Entity entities : entity.getWorld().getNearbyEntities(entity.getLocation(), 3, 3, 3)) {
                                        if (entities instanceof LivingEntity livingEntity) {
                                            if (livingEntity != damager && !(entity instanceof Wolf wolf && wolf.getOwner().equals(damager))) {
                                                livingEntity.damage(e.getDamage() * 0.2);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            lightning.put(uuid, 0L);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Entity entity : Bukkit.getWorlds().get(0).getEntitiesByClass(Entity.class)) {
                UUID uuid = entity.getUniqueId();
                Location location = entity.getLocation();
                if (lightning.containsKey(uuid)) {
                    if (System.currentTimeMillis() - lightning.get(uuid) <= 3000) {
                        entity.getWorld().playEffect(location.add(0, 1, 0), Effect.MAGIC_CRIT, 0);
                    }
                } else {
                    lightning.put(uuid, 0L);
                }
            }
        }, 0, 20);
    }
}
