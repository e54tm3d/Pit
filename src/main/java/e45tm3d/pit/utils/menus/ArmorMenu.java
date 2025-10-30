package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.ItemBuilder;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ArmorMenu {

    public static void open(Player p) {

        p.closeInventory();

        int level_helmet = User.getHelmetLevel(p);
        int level_chestplate = User.getChestplateLevel(p);
        int level_leggings = User.getLeggingsLevel(p);
        int level_boots = User.getBootsLevel(p);

        String helmet_level = (level_helmet < 5) ?
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.price")
                        .replaceAll("%price%", String.valueOf(Yaml.ARMOR.getConfig().getInt("menu.items.helmet.costs.tier_" + (level_helmet + 1))))
                        .replaceAll("&", "§") :
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.level_max")
                        .replaceAll("&", "§");

        String chestplate_level = (level_chestplate < 5) ?
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.price")
                        .replaceAll("%price%", String.valueOf(Yaml.ARMOR.getConfig().getInt("menu.items.chestplate.costs.tier_" + (level_chestplate + 1))))
                        .replaceAll("&", "§") :
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.level_max")
                        .replaceAll("&", "§");

        String leggings_level = (level_leggings < 5) ?
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.price")
                        .replaceAll("%price%", String.valueOf(Yaml.ARMOR.getConfig().getInt("menu.items.leggings.costs.tier_" + (level_leggings + 1))))
                        .replaceAll("&", "§") :
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.level_max")
                        .replaceAll("&", "§");

        String boots_level = (level_boots < 5) ?
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.price")
                        .replaceAll("%price%", String.valueOf(Yaml.ARMOR.getConfig().getInt("menu.items.boots.costs.tier_" + (level_boots + 1))))
                        .replaceAll("&", "§") :
                Yaml.ARMOR.getConfig().getString("menu.settings.cost_format.level_max")
                        .replaceAll("&", "§");


        Inventory inv = Bukkit.createInventory(null, 54, Yaml.ARMOR.getConfig().getString("menu.title").replaceAll("&", "§"));

        PlaceholdersItemBuilder locked = new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7), p).setName("§7");

        PlaceholdersItemBuilder unlocked = new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5), p).setName("§7");

        inv.setItem(12, locked.build());
        inv.setItem(13, locked.build());
        inv.setItem(14, locked.build());
        inv.setItem(15, locked.build());
        inv.setItem(16, locked.build());
        inv.setItem(21, locked.build());
        inv.setItem(22, locked.build());
        inv.setItem(23, locked.build());
        inv.setItem(24, locked.build());
        inv.setItem(25, locked.build());
        inv.setItem(30, locked.build());
        inv.setItem(31, locked.build());
        inv.setItem(32, locked.build());
        inv.setItem(33, locked.build());
        inv.setItem(34, locked.build());
        inv.setItem(39, locked.build());
        inv.setItem(40, locked.build());
        inv.setItem(41, locked.build());
        inv.setItem(42, locked.build());
        inv.setItem(43, locked.build());

        PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER, 1), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.close.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.close.lore"));

        PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD, 1), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.vault.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.vault.lore"));

        PlaceholdersItemBuilder helmet = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.helmet.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.helmet.lore"))
                .replaceStringFromLore("%level%", String.valueOf(level_helmet))
                .replaceLore("%cost_format%", List.of(helmet_level));

        switch (level_helmet) {
            case 1 -> helmet.setMaterial(Material.LEATHER_HELMET);
            case 2 -> helmet.setMaterial(Material.GOLD_HELMET);
            case 3 -> helmet.setMaterial(Material.CHAINMAIL_HELMET);
            case 4 -> helmet.setMaterial(Material.IRON_HELMET);
            case 5 -> helmet.setMaterial(Material.DIAMOND_HELMET);
        }

        inv.setItem(10, helmet.build());

        if (level_helmet >= 1) inv.setItem(12, unlocked.build());
        if (level_helmet >= 2) inv.setItem(13, unlocked.build());
        if (level_helmet >= 3) inv.setItem(14, unlocked.build());
        if (level_helmet >= 4) inv.setItem(15, unlocked.build());
        if (level_helmet >= 5) inv.setItem(16, unlocked.build());

        PlaceholdersItemBuilder chestplate = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.chestplate.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.chestplate.lore"))
                .replaceStringFromLore("%level%", String.valueOf(level_chestplate))
                .replaceLore("%cost_format%", List.of(chestplate_level));

        switch (level_chestplate) {
            case 1 -> chestplate.setMaterial(Material.LEATHER_CHESTPLATE);
            case 2 -> chestplate.setMaterial(Material.GOLD_CHESTPLATE);
            case 3 -> chestplate.setMaterial(Material.CHAINMAIL_CHESTPLATE);
            case 4 -> chestplate.setMaterial(Material.IRON_CHESTPLATE);
            case 5 -> chestplate.setMaterial(Material.DIAMOND_CHESTPLATE);
        }

        inv.setItem(19, chestplate.build());

        if (level_chestplate >= 1) inv.setItem(21, unlocked.build());
        if (level_chestplate >= 2) inv.setItem(22, unlocked.build());
        if (level_chestplate >= 3) inv.setItem(23, unlocked.build());
        if (level_chestplate >= 4) inv.setItem(24, unlocked.build());
        if (level_chestplate >= 5) inv.setItem(25, unlocked.build());

        PlaceholdersItemBuilder leggings = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.leggings.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.leggings.lore"))
                .replaceStringFromLore("%level%", String.valueOf(level_leggings))
                .replaceLore("%cost_format%", List.of(leggings_level));

        switch (level_leggings) {
            case 1 -> leggings.setMaterial(Material.LEATHER_LEGGINGS);
            case 2 -> leggings.setMaterial(Material.GOLD_LEGGINGS);
            case 3 -> leggings.setMaterial(Material.CHAINMAIL_LEGGINGS);
            case 4 -> leggings.setMaterial(Material.IRON_LEGGINGS);
            case 5 -> leggings.setMaterial(Material.DIAMOND_LEGGINGS);
        }

        inv.setItem(28, leggings.build());

        if (level_leggings >= 1) inv.setItem(30, unlocked.build());
        if (level_leggings >= 2) inv.setItem(31, unlocked.build());
        if (level_leggings >= 3) inv.setItem(32, unlocked.build());
        if (level_leggings >= 4) inv.setItem(33, unlocked.build());
        if (level_leggings >= 5) inv.setItem(34, unlocked.build());

        PlaceholdersItemBuilder boots = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(Yaml.ARMOR.getConfig().getString("menu.items.boots.name"))
                .setLore(Yaml.ARMOR.getConfig().getStringList("menu.items.boots.lore"))
                .replaceStringFromLore("%level%", String.valueOf(level_boots))
                .replaceLore("%cost_format%", List.of(boots_level));
        switch (level_boots) {
            case 1 -> boots.setMaterial(Material.LEATHER_BOOTS);
            case 2 -> boots.setMaterial(Material.GOLD_BOOTS);
            case 3 -> boots.setMaterial(Material.CHAINMAIL_BOOTS);
            case 4 -> boots.setMaterial(Material.IRON_BOOTS);
            case 5 -> boots.setMaterial(Material.DIAMOND_BOOTS);
        }

        inv.setItem(37, boots.build());

        if (level_boots >= 1) inv.setItem(39, unlocked.build());
        if (level_boots >= 2) inv.setItem(40, unlocked.build());
        if (level_boots >= 3) inv.setItem(41, unlocked.build());
        if (level_boots >= 4) inv.setItem(42, unlocked.build());
        if (level_boots >= 5) inv.setItem(43, unlocked.build());

        inv.setItem(49, close.build());
        inv.setItem(48, vault.build());

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "armor");
    }
}