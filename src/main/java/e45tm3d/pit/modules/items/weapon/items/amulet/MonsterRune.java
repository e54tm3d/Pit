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
import e45tm3d.pit.utils.functions.PlayerFunction;
import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MonsterRune extends WeaponModule {

    @Override
    public String getIdentifier() {
        return "monster_rune";
    }

    @Override
    public WeaponType getType() {
        return WeaponType.AMULET;
    }

    @Override
    public ItemStack getMenuItem() {
        return WeaponMenuItems.MONSTER_RUNE.getItemStack();
    }

    @Override
    public ItemStack getItem() {
        return WeaponItems.MONSTER_RUNE.getItemStack();
    }

    @Override
    public int getSlot() {
        return 30;
    }

    @Override
    public int getTierPrice(int tier) {
        switch (tier) {
            case 1 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.monster_rune.tier_1.price");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.monster_rune.tier_2.price");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.monster_rune.tier_3.price");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getInt("upgrade.monster_rune.tier_4.price");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_1.name");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_2.name");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_3.name");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_4.name");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_1.consume_items");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_2.consume_items");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_3.consume_items");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_4.consume_items");
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
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_1.lore");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_2.lore");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_3.lore");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getStringList("upgrade.monster_rune.tier_4.lore");
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
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_1.upgrade");
            }
            case 2 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_2.upgrade");
            }
            case 3 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_3.upgrade");
            }
            case 4 -> {
                return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_4.upgrade");
            }
            default -> {
                return Collections.emptyList().toString();
            }
        }
    }

    @Override
    public String getTierLevelmaxCostFormat(int tier) {
        if (tier == 4) {
            return Yaml.WEAPON_UPDATE.getConfig().getString("upgrade.monster_rune.tier_4.levelmax");
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
        } else if (event instanceof EntityTargetLivingEntityEvent e) {

            Entity entity = e.getEntity();

            if (entity instanceof LivingEntity livingEntity) {
                if (e.getTarget() instanceof Player p) {
                    if (User.getWeaponLevel(p, getIdentifier()) >= 2) {
                        if (livingEntity.getMaxHealth() <= 10) {
                            handleCancelTarget(e);
                        }
                    }
                    if (User.getWeaponLevel(p, getIdentifier()) >= 3) {
                        if (livingEntity.getMaxHealth() <= 20) {
                            handleCancelTarget(e);
                        }
                    }
                    if (User.getWeaponLevel(p, getIdentifier()) >= 4) {
                        if (livingEntity.getMaxHealth() <= p.getMaxHealth() * 2) {
                            handleCancelTarget(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void run(WeaponModule task) {
    }

    private void handleCancelTarget(EntityTargetLivingEntityEvent e) {
        if (e.getTarget() instanceof Player p) {
            if (containItemAtLeast(p, 1)) {

                for (int i = 0; i < 5; i++) {

                    double offsetX = MathFunction.randomDouble(1.5, -1.5);
                    double offsetY = MathFunction.randomDouble(1.5, -1.5);
                    double offsetZ = MathFunction.randomDouble(1.5, -1.5);

                    Vector offset = new Vector(offsetX, offsetY, offsetZ);

                    PlayerFunction.spawnSingleParticle(EnumParticle.ENCHANTMENT_TABLE, p.getLocation().add(0, 1, 0).add(offset), p);

                }

                e.setCancelled(true);
            }
        }
    }

}
