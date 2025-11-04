package e45tm3d.pit.utils.menus.second_menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.ItemBuilder;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.functions.MathFunction;
import e45tm3d.pit.utils.maps.PlayerMaps;
import e45tm3d.pit.utils.maps.WeaponMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WeaponUpdateMenu {

    public static void open(Player p, String type) {

        if (User.isPlaying(p)) {

            String inventoryTitle = Yaml.WEAPON_UPDATE.getConfig().getString("menu.title");

            if (inventoryTitle == null) inventoryTitle = "Sword Update Menu";

            Inventory inv = Bukkit.createInventory(null, 36, inventoryTitle);

            int level = User.getWeaponLevel(p, type);

            UUID uuid = p.getUniqueId();

            p.closeInventory();

            String upgradeMsg;
            if (User.getWeaponLevel(p, type) < 4) {
                String upgradeFormat = WeaponMaps.getTierUpgradeCostFormat(type, level + 1);
                Integer upgradePrice = WeaponMaps.getTierPrice(type, level + 1);
                if (upgradeFormat == null) {
                    upgradeMsg = "Upgrade to Tier " + (level + 1);
                } else {
                    upgradeMsg = upgradeFormat
                            .replaceAll("&", "§")
                            .replaceAll("%price%", String.valueOf(upgradePrice != null ? upgradePrice : 0));
                }
            } else {
                String maxLevelFormat = WeaponMaps.getTierLevelmaxCostFormat(type, level);
                if (maxLevelFormat == null) {
                    upgradeMsg = "Max Level Reached";
                } else {
                    upgradeMsg = maxLevelFormat
                            .replaceAll("&", "§");
                }
            }

            PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER, 1), p)
                    .setName(Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.close.name"))
                    .setLore(Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.close.lore"));

            PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD, 1), p)
                    .setName(Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.vault.name"))
                    .setLore(Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.vault.lore"));

            PlaceholdersItemBuilder obtain = new PlaceholdersItemBuilder(new ItemStack(Material.LEATHER, 1), p)
                    .setName(Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.obtain.name"))
                    .setLore(Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.obtain.lore"));

            PlaceholdersItemBuilder upgrade = new PlaceholdersItemBuilder(new ItemStack(Material.EXP_BOTTLE, 1), p)
                    .setName(Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.upgrade.name"))
                    .setLore(Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.upgrade.lore"))
                    .replaceLore("%cost_format%", List.of(upgradeMsg));

            for (int i = 1; i <= 4; i++) {

                PlaceholdersItemBuilder progress = new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), p)
                        .setName(WeaponMaps.getTierName(type, i).replaceAll("%tier%", MathFunction.intToRoman(i)))
                        .setLore(WeaponMaps.getTierLore(type, i))
                        .replaceStringFromLore("%tier%", "dwa");

                if (i <= level)
                    progress = new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), p)
                            .setName(WeaponMaps.getTierName(type, i).replaceAll("%tier%", MathFunction.intToRoman(i)))
                            .setLore(WeaponMaps.getTierLore(type, i));

                inv.setItem(11 + i, progress.build());
            }

            inv.setItem(31, close.build());
            inv.setItem(30, vault.build());
            inv.setItem(27, obtain.build());
            inv.setItem(11, upgrade.build());

            p.openInventory(inv);

            PlayerMaps.menu.put(uuid, "weapon_update_" + type);
        }
    }
}