package e45tm3d.pit.modules.items.weapon.items.amulet;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.items.weapon.WeaponModule;
import e45tm3d.pit.modules.items.weapon.WeaponType;
import e45tm3d.pit.utils.enums.weapon.WeaponItems;
import e45tm3d.pit.utils.enums.weapon.WeaponMenuItems;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.functions.MathFunction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GoldAmulet extends WeaponModule {

    @Override
    public String getIdentifier() {
        return "gold_amulet";
    }

    @Override
    public WeaponType getType() {
        return WeaponType.AMULET;
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.GOLD_AMULET.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.GOLD_AMULET.getItemStack();
    }

    @Override
    public int getSlot() {
        return 28;
    }

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.gold_amulet.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.gold_amulet.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.gold_amulet.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.gold_amulet.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.gold_amulet.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.gold_amulet.tier_4.levelmax");
        }
        return Collections.emptyList().toString();
    }

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerInteractEvent e) {

            Action action = e.getAction();
            Player p = e.getPlayer();

            if (usingItem(p) && action.equals(Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
            }
        } else if (event instanceof PlayerMurderEvent e) {

            Player dead = e.getDead();

            if (e.getKiller() instanceof Player p) {
                if (containItemAtLeast(p, 1)) {
                    if (User.getWeaponLevel(p, "gold_amulet") >= 4) {
                        dead.getWorld().dropItem(dead.getLocation(), new ItemStack(Material.GOLD_NUGGET));
                    }
                }
            }

        }
    }

    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (containItemAtLeast(p, 1)) {

                    if (User.getWeaponLevel(p, "gold_amulet") >= 2) {

                        int chance = User.getWeaponLevel(p, "gold_amulet") >= 3 ? 30 : 50;

                        Random r = new Random();

                        if (r.nextInt(100) < chance) {

                            double offsetX = MathFunction.randomDouble(10, -10);
                            double offsetY = MathFunction.randomDouble(10, -10);
                            double offsetZ = MathFunction.randomDouble(10, -10);

                            Vector offset = new Vector(offsetX, offsetY, offsetZ);

                            Item item = p.getWorld().dropItem(p.getLocation().add(offset), new ItemStack(Material.GOLD_NUGGET));

                            item.setItemStack(ItemFunction.addNBTTag(item.getItemStack(), UUID.randomUUID().toString()));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), item::remove, 600);
                        }
                    }
                }
            }
        }, 0L, 60);
    }
}
