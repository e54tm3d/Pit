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
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ExperienceAmulet extends WeaponModule {

    @Override
    public String getIdentifier() {
        return "experience_amulet";
    }

    @Override
    public WeaponType getType() {
        return WeaponType.AMULET;
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.EXPERIENCE_AMULET.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.EXPERIENCE_AMULET.getItemStack();
    }

    @Override
    public int getSlot() {
        return 29;
    }

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.experience_amulet.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.experience_amulet.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.experience_amulet.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.experience_amulet.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.experience_amulet.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.experience_amulet.tier_4.levelmax");
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
        } else if (event instanceof PlayerPickupItemEvent e) {

            Player p = e.getPlayer();
            Item item = e.getItem();

            if (ItemFunction.hasNBTTag(item.getItemStack(), "experience")) {

                int boost = User.getWeaponLevel(p, "experience_amulet") >= 4 ? (int) MathFunction.randomDouble(100, 50) : 0;

                int amount = (int) MathFunction.randomDouble(250 + boost, 100 + boost);

                e.setCancelled(true);
                e.getItem().remove();

                User.setExp(p, User.getExp(p) + amount);
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
            }
        }
    }

    @Override
    public void run(WeaponModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (containItemAtLeast(p, 1)) {

                    if (User.getWeaponLevel(p, "experience_amulet") >= 2) {

                        int chance = User.getWeaponLevel(p, "experience_amulet") >= 3 ? 30 : 50;

                        Random r = new Random();

                        if (r.nextInt(100) < chance) {

                            double offsetX = MathFunction.randomDouble(10, -10);
                            double offsetY = MathFunction.randomDouble(10, -10);
                            double offsetZ = MathFunction.randomDouble(10, -10);

                            Vector offset = new Vector(offsetX, offsetY, offsetZ);

                            ItemStack item = ItemFunction.getBase64Head("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmRkMDdkYzE0MTBiMzQzODg0MTQ0NmNkY2UyYmFmYmE0ZmM2MTk5NTQ0NWZlMzA4NGY1YjA3NWY5MjZlYmNhMSJ9fX0=");
                            item = ItemFunction.addNBTTag(item, "experience");

                            Item drop = p.getWorld().dropItem(p.getLocation().add(offset), item);

                            drop.setItemStack(ItemFunction.addNBTTag(drop.getItemStack(), UUID.randomUUID().toString()));

                            Bukkit.getScheduler().scheduleSyncDelayedTask(ThePit.getInstance(), drop::remove, 600);
                        }
                    }
                }
            }
        }, 0L, 100);
    }
}
