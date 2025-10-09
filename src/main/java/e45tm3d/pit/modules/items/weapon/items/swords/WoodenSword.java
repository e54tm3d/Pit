package e45tm3d.pit.modules.items.weapon.items.swords;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class WoodenSword extends WeaponModule {

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_sword.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_sword.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_sword.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.wooden_sword.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.wooden_sword.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.wooden_sword.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public String getType() {
        return "wooden_sword";
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.WOODEN_SWORD.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.WOODEN_SWORD.getItemStack();
    }

    @Override
    public int getSlot() {
        return 10;
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerJoinEvent e) {
            Player p = e.getPlayer();
            if (!p.hasPlayedBefore()) {
                if (!hasItem(p)) {
                    p.getInventory().addItem(ItemFunction.searchItem(getType()));
                    User.setWeaponLevel(p, getType(), 1);
                }
            }
        } else if (event instanceof EntityDamageByEntityEvent e) {
            if (e.getDamager() instanceof Player p) {

                if (usingItem(p)) {

                    if (User.getWeaponLevel(p, getType()) >= 2)  {
                        e.setDamage(e.getDamage() + 1);
                    }
                    if (User.getWeaponLevel(p, getType()) >= 3)  {
                        e.setDamage(e.getDamage() + 1);
                    }
                }
            }
        }
    }

    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (usingItem(p)) {
                    if (User.getWeaponLevel(p, getType()) >= 4)
                        p.addPotionEffect(PotionEffectType.SPEED.createEffect(100, 0), true);
                }
            }
        }, 60, 60);
    }
}
