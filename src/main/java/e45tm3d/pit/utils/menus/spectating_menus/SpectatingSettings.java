package e45tm3d.pit.utils.menus.spectating_menus;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.lists.PlayerLists;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class SpectatingSettings {

    public static void open(Player p) {

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 27, Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.title").replaceAll("&", "§"));

        if (!User.isPlaying(p)) {

            boolean is_night_vision = PlayerMaps.night_vision.getOrDefault(p.getUniqueId(), false);
            boolean is_flight = PlayerMaps.flight.getOrDefault(p.getUniqueId(), false);
            boolean is_always_flight = PlayerMaps.always_flight.getOrDefault(p.getUniqueId(), false);
            boolean is_speed_1 = PlayerMaps.speed.getOrDefault(p.getUniqueId(), 0) == 1;
            boolean is_speed_2 = PlayerMaps.speed.getOrDefault(p.getUniqueId(), 0) == 2;
            boolean is_speed_3 = PlayerMaps.speed.getOrDefault(p.getUniqueId(), 0) == 3;
            boolean is_speed_4 = PlayerMaps.speed.getOrDefault(p.getUniqueId(), 0) == 4;
            boolean is_jump_boost_3 = PlayerMaps.jump_boost.getOrDefault(p.getUniqueId(), 0) == 3;
            boolean is_jump_boost_5 = PlayerMaps.jump_boost.getOrDefault(p.getUniqueId(), 0) == 5;

            PlaceholdersItemBuilder night_vision = new PlaceholdersItemBuilder(new ItemStack(Material.EYE_OF_ENDER), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.night_vision.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.night_vision.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder speed_i = new PlaceholdersItemBuilder(new ItemStack(Material.LEATHER_BOOTS), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.speed_i.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.speed_i.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder speed_ii = new PlaceholdersItemBuilder(new ItemStack(Material.IRON_BOOTS), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.speed_ii.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.speed_ii.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder speed_iii = new PlaceholdersItemBuilder(new ItemStack(Material.GOLD_BOOTS), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.speed_iii.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.speed_iii.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder speed_iv = new PlaceholdersItemBuilder(new ItemStack(Material.DIAMOND_BOOTS), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.speed_iv.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.speed_iv.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder flight = new PlaceholdersItemBuilder(new ItemStack(Material.FEATHER), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.flight.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.flight.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder alway_flight = new PlaceholdersItemBuilder(new ItemStack(Material.FEATHER), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.alway_flight.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.alway_flight.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder jump_boost_iii = new PlaceholdersItemBuilder(new ItemStack(Material.RABBIT_FOOT), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.jump_boost_iii.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.jump_boost_iii.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder jump_boost_v = new PlaceholdersItemBuilder(new ItemStack(Material.RABBIT_FOOT), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.jump_boost_v.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.jump_boost_v.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder reset = new PlaceholdersItemBuilder(new ItemStack(Material.SNOW_BALL), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.reset.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.reset.lore"))
                    .setIdentifier("ui_item");

            PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.SPECTATING.getConfig().getString("menus.spectating_setting.items.close.name"))
                    .setLore(Yaml.SPECTATING.getConfig().getStringList("menus.spectating_setting.items.close.lore"))
                    .setIdentifier("ui_item");

            inv.setItem(2, is_night_vision ? night_vision.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : night_vision.build());
            inv.setItem(3, is_speed_1 ? speed_i.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : speed_i.build());
            inv.setItem(4, is_speed_2 ? speed_ii.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : speed_ii.build());
            inv.setItem(5, is_speed_3 ? speed_iii.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : speed_iii.build());
            inv.setItem(6, is_speed_4 ? speed_iv.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : speed_iv.build());
            inv.setItem(11, is_flight ? flight.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : flight.build());
            inv.setItem(12, is_always_flight ? alway_flight.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : alway_flight.build());
            inv.setItem(13, is_jump_boost_3 ? jump_boost_iii.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : jump_boost_iii.build());
            inv.setItem(14, is_jump_boost_5 ? jump_boost_v.addEnchancement(Enchantment.DURABILITY, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build() : jump_boost_v.build());

            inv.setItem(21, reset.build());
            inv.setItem(22, close.build());
        }
        p.openInventory(inv);
        PlayerMaps.menu.put(p.getUniqueId(), "spectating_settings");
    }
}