package e45tm3d.pit.modules.items.weapon.items.bows;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;

public class WoodenBow extends WeaponModule {

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_bow.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_bow.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_bow.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_bow.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_bow.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_bow.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getType() {
        return "wooden_bow";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.WOODEN_BOW.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.WOODEN_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 19;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof EntityShootBowEvent e) {
            if (e.getEntity() instanceof Player p) {

                if (usingItem(p)) {

                    if (User.getWeaponLevel(p, getType()) == 2) {
                        e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(1.2));
                    } else if (User.getWeaponLevel(p, getType()) >= 3) {
                        e.getProjectile().setMetadata("DAMAGE", new FixedMetadataValue(ThePit.getInstance(), 1.2));
                    }
                }
            }
        } else if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getEntity() instanceof Player p) {
                if (User.getWeaponLevel(p, getType()) >= 4) {
                    if (usingItem(p)) {
                        Bukkit.getScheduler().runTaskLaterAsynchronously(ThePit.getInstance(), () -> {
                            Vector vec = p.getVelocity();
                            double y = p.getVelocity().getY();
                            vec = vec.multiply(1.5);
                            vec = vec.setY(y);
                            p.setVelocity(vec);
                        }, 1);
                    }
                }
            }
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

    }
}
