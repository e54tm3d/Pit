package e45tm3d.pit.modules.items.weapon.items.bows;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.modules.items.weapon.WeaponType;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class ArtemisBow extends WeaponModule {

    @Override
    public WeaponType getType() {
        return WeaponType.NORMAL;
    }

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.artemis_bow.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.artemis_bow.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.artemis_bow.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.artemis_bow.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.artemis_bow.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.artemis_bow.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getIdentifier() {
        return "artemis_bow";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.ARTEMIS_BOW.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.ARTEMIS_BOW.getItemStack();
    }

    @Override
    public int getSlot() {
        return 20;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityShootBowEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (usingItem(p)) {
                    int weaponLevel = User.getWeaponLevel(p, getIdentifier());

                    if (weaponLevel >= 2) {
                        e.getProjectile().setMetadata("AUTO_AIM", new FixedMetadataValue(ThePit.getInstance(), true));
                    }

                    if (weaponLevel >= 3) {
                        e.getProjectile().setMetadata("DAMAGE", new FixedMetadataValue(ThePit.getInstance(), 1.5));
                    }

                    if (weaponLevel >= 4) {
                        Location loc = p.getLocation().add(0, 1.8, 0);

                        for (int i = -10; i <= 10; i += 10) {
                            if (i != 0) {

                                Location tempLoc = loc.clone();

                                tempLoc.setYaw(p.getLocation().getYaw() + i);

                                Vector direction = tempLoc.getDirection().multiply(0.2);

                                Arrow arrow = (Arrow) e.getEntity().getWorld().spawnEntity(
                                        e.getProjectile().getLocation().add(direction), EntityType.ARROW);

                                arrow.setShooter(p);

                                Vector velocity = tempLoc.getDirection().multiply(e.getProjectile().getVelocity().length() * 0.5);
                                arrow.setVelocity(velocity);
                                if (e.getProjectile() instanceof Arrow a) {
                                    arrow.setCritical(a.isCritical());
                                }
                            }
                        }
                    }
                }
            }
        } else if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Arrow arrow) {
                if (arrow.hasMetadata("DAMAGE")) {
                    double damage = arrow.getMetadata("DAMAGE").get(0).asDouble();
                    e.setDamage(e.getDamage() * damage);
                }
            }
        }
    }
    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            // 遍历所有世界中的箭矢
            for (org.bukkit.World world : Bukkit.getWorlds()) {
                for (Projectile projectile : world.getEntitiesByClass(Projectile.class)) {
                    if (projectile.hasMetadata("AUTO_AIM")) {

                        LivingEntity shooter = null;
                        if (projectile.getShooter() instanceof LivingEntity) {
                            shooter = (LivingEntity) projectile.getShooter();
                        }

                        LivingEntity nearestTarget = null;
                        double nearestDistance = 10.0;

                        for (Entity entity : projectile.getNearbyEntities(nearestDistance, nearestDistance, nearestDistance)) {
                            if (entity instanceof LivingEntity && entity != shooter && !(entity instanceof Wolf wolf && wolf.getOwner().equals(shooter))) {

                                double distance = projectile.getLocation().distance(entity.getLocation());
                                if (distance < nearestDistance) {
                                    nearestDistance = distance;
                                    nearestTarget = (LivingEntity) entity;
                                }
                            }
                        }

                        if (nearestTarget != null) {
                            try {
                                // 计算理想的追踪方向
                                Vector targetPos = nearestTarget.getLocation().toVector();
                                Vector desiredDirection = targetPos.subtract(projectile.getLocation().toVector());

                                // 保留部分Y轴方向，提高稳定性
                                double currentYVelocity = projectile.getVelocity().getY();
                                desiredDirection.setY(desiredDirection.getY() * 0.3 + currentYVelocity * 0.7);

                                // 标准化理想方向
                                if (desiredDirection.length() > 0) {
                                    desiredDirection = desiredDirection.normalize();
                                }

                                // 获取当前箭矢速度
                                Vector currentVelocity = projectile.getVelocity();
                                double currentSpeed = currentVelocity.length();

                                // 计算当前方向与理想方向的差值
                                Vector directionDifference = desiredDirection.subtract(currentVelocity.normalize());

                                // 应用加速度，控制转向的快慢
                                double accelerationFactor = 0.2; // 加速度因子，值越小转向越慢
                                Vector newVelocity = currentVelocity.add(directionDifference.multiply(accelerationFactor));

                                // 保持箭矢原有速度大小
                                if (newVelocity.length() > 0) {
                                    newVelocity = newVelocity.normalize().multiply(currentSpeed);
                                }

                                // 设置新的速度向量
                                projectile.setVelocity(newVelocity);
                            } catch (Exception e) {
                                // 防止任何计算异常导致整个功能失效
                                ThePit.getInstance().getLogger().warning("Artemis' Bow tracking error: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        }, 0, 1);
    }
}