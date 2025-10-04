package e45tm3d.pit.modules.items.weapon.items.swords;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.EntityFrozenEvent;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class IceSword extends WeaponModule {

    private Map<UUID, Long> forzen = new HashMap<>();

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.ice_sword.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.ice_sword.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.ice_sword.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.ice_sword.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.ice_sword.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.ice_sword.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getType() {
        return "ice_sword";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.ICE_SWORD.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.ICE_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 13;
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
                                Bukkit.getPluginManager().callEvent(new EntityFrozenEvent(entity));
                                entity.addPotionEffect(PotionEffectType.SLOW.createEffect(40, 1));
                                forzen.put(uuid, System.currentTimeMillis());
                                entity.setHealth(Math.min(entity.getHealth() + 1, entity.getMaxHealth()));
                            }
                        }
                        if (User.getWeaponLevel(damager, getType()) >= 3) e.setDamage(e.getDamage() * 1.2);
                        if (User.getWeaponLevel(damager, getType()) >= 4) {
                            if (forzen.containsKey(uuid)) {
                                if (System.currentTimeMillis() - forzen.get(entity.getUniqueId()) <= 3000) {
                                    e.setDamage(e.getDamage() * 2);
                                }
                            } else {
                                forzen.put(uuid, 0L);
                            }
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
                if (forzen.containsKey(uuid)) {
                    if (System.currentTimeMillis() - forzen.get(uuid) <= 3000) {
                        entity.getWorld().playEffect(entity.getLocation().add(0, 1, 0), Effect.SNOWBALL_BREAK, 1);
                        entity.getWorld().playSound(entity.getLocation(), Sound.GLASS, 1F, 1F);
                    }
                } else {
                    forzen.put(uuid, 0L);
                }
            }
        }, 0, 20);
    }
}
