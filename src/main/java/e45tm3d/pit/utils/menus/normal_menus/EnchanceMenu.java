package e45tm3d.pit.utils.menus.normal_menus;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class EnchanceMenu {

    public static void open(Player p) {

        if (User.isPlaying(p)) {

            int level_helmet = User.getHelmetLevel(p);
            int level_chestplate = User.getChestplateLevel(p);
            int level_leggings = User.getLeggingsLevel(p);
            int level_boots = User.getBootsLevel(p);

            p.closeInventory();

            Inventory inv = Bukkit.createInventory(null, 18, Yaml.ENCHANCE.getConfig().getString("menu.title"));

            PlaceholdersItemBuilder weapon = new PlaceholdersItemBuilder(new ItemStack(Material.IRON_SWORD), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.weapon.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.weapon.lore"))
                    .replaceLore("%enchance%", EnchanceMaps.enchances.get(User.getEnchance(p, "weapon")))
                    .replaceStringFromLore("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.weapon.price")))
                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder helmet = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.helmet.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.helmet.lore"))
                    .replaceLore("%enchance%", EnchanceMaps.enchances.get(User.getEnchance(p, "helmet")))
                    .replaceStringFromLore("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.helmet.price")))
                    .setIdentifier("ui_item");

            switch (level_helmet) {
                case 1 -> helmet.setMaterial(Material.LEATHER_HELMET);
                case 2 -> helmet.setMaterial(Material.GOLD_HELMET);
                case 3 -> helmet.setMaterial(Material.CHAINMAIL_HELMET);
                case 4 -> helmet.setMaterial(Material.IRON_HELMET);
                case 5 -> helmet.setMaterial(Material.DIAMOND_HELMET);
            }

            PlaceholdersItemBuilder chestplate = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.chestplate.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.chestplate.lore"))
                    .replaceLore("%enchance%", EnchanceMaps.enchances.get(User.getEnchance(p, "chestplate")))
                    .replaceStringFromLore("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.chestplate.price")))
                    .setIdentifier("ui_item");

            switch (level_chestplate) {
                case 1 -> chestplate.setMaterial(Material.LEATHER_CHESTPLATE);
                case 2 -> chestplate.setMaterial(Material.GOLD_CHESTPLATE);
                case 3 -> chestplate.setMaterial(Material.CHAINMAIL_CHESTPLATE);
                case 4 -> chestplate.setMaterial(Material.IRON_CHESTPLATE);
                case 5 -> chestplate.setMaterial(Material.DIAMOND_CHESTPLATE);
            }

            PlaceholdersItemBuilder leggings = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.leggings.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.leggings.lore"))
                    .replaceLore("%enchance%", EnchanceMaps.enchances.get(User.getEnchance(p, "leggings")))
                    .replaceStringFromLore("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.leggings.price")))
                    .setIdentifier("ui_item");

            switch (level_leggings) {
                case 1 -> leggings.setMaterial(Material.LEATHER_LEGGINGS);
                case 2 -> leggings.setMaterial(Material.GOLD_LEGGINGS);
                case 3 -> leggings.setMaterial(Material.CHAINMAIL_LEGGINGS);
                case 4 -> leggings.setMaterial(Material.IRON_LEGGINGS);
                case 5 -> leggings.setMaterial(Material.DIAMOND_LEGGINGS);
            }

            PlaceholdersItemBuilder boots = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.boots.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.boots.lore"))
                    .replaceLore("%enchance%", EnchanceMaps.enchances.get(User.getEnchance(p, "boots")))
                    .replaceStringFromLore("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.boots.price")))
                    .setIdentifier("ui_item");

            switch (level_boots) {
                case 1 -> boots.setMaterial(Material.LEATHER_BOOTS);
                case 2 -> boots.setMaterial(Material.GOLD_BOOTS);
                case 3 -> boots.setMaterial(Material.CHAINMAIL_BOOTS);
                case 4 -> boots.setMaterial(Material.IRON_BOOTS);
                case 5 -> boots.setMaterial(Material.DIAMOND_BOOTS);
            }

            PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.close.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.close.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD), p)
                    .setName(Yaml.ENCHANCE.getConfig().getString("menu.items.vault.name"))
                    .setLore(Yaml.ENCHANCE.getConfig().getStringList("menu.items.vault.lore"))
                    .setIdentifier("ui_item");

            inv.setItem(2, weapon.build());
            inv.setItem(3, helmet.build());
            inv.setItem(4, chestplate.build());
            inv.setItem(5, leggings.build());
            inv.setItem(6, boots.build());

            inv.setItem(12, vault.build());
            inv.setItem(13, close.build());

            p.openInventory(inv);

            PlayerMaps.menu.put(p.getUniqueId(), "enchance");

        }
    }
}