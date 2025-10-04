package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class EnchanceMenu {

    public static void open(Player p) {

        int level_helmet = User.getHelmetLevel(p);
        int level_chestplate = User.getChestplateLevel(p);
        int level_leggings = User.getLeggingsLevel(p);
        int level_boots = User.getBootsLevel(p);

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 18, Yaml.ENCHANCE.getConfig().getString("menu.title"));

        ItemStack weapon = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta weaponMeta = weapon.getItemMeta();
        weaponMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.weapon.name").replaceAll("&", "§"));
        ArrayList<String> weaponLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.weapon.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.weapon.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.weapon.lore")) {
                if (lore.equalsIgnoreCase("%enchance%")) {
                    // 处理 %enchance% 占位符，替换为实际的增强效果
                    String enchantType = User.getEnchance(p, "weapon");
                    boolean empty = !EnchanceList.enchances.contains(enchantType);
                    if (empty) User.setEnchance(p, "weapon", "none");
                    if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            weaponLores.add(enchance.replaceAll("&", "§"));
                        }
                    }
                    // 跳过添加占位符本身
                    continue;
                }
                weaponLores.add(lore
                        .replaceAll("&", "§")
                        .replaceAll("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.weapon.price")))
                );
            }
            weaponMeta.setLore(weaponLores);
        }
        weapon.setItemMeta(weaponMeta);

        ItemStack helmet = new ItemStack(Material.BARRIER, 1);
        switch (level_helmet) {
            case 1 -> helmet = new ItemStack(Material.LEATHER_HELMET, 1);
            case 2 -> helmet = new ItemStack(Material.GOLD_HELMET, 1);
            case 3 -> helmet = new ItemStack(Material.CHAINMAIL_HELMET, 1);
            case 4 -> helmet = new ItemStack(Material.IRON_HELMET, 1);
            case 5 -> helmet = new ItemStack(Material.DIAMOND_HELMET, 1);
        }
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.helmet.name").replaceAll("&", "§"));
        ArrayList<String> helmetLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.helmet.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.helmet.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.helmet.lore")) {
                if (lore.equalsIgnoreCase("%enchance%")) {
                    // 处理 %enchance% 占位符，替换为实际的增强效果
                    String enchantType = User.getEnchance(p, "helmet");
                    boolean empty = !EnchanceList.enchances.contains(enchantType);
                    if (empty) User.setEnchance(p, "helmet", "none");
                    if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            helmetLores.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    }
                    // 跳过添加占位符本身
                    continue;
                }
                helmetLores.add(lore
                        .replaceAll("&", "§")
                        .replaceAll("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.helmet.price")))
                );
            }
            helmetMeta.setLore(helmetLores);
        }
        helmet.setItemMeta(helmetMeta);

        ItemStack chestplate = new ItemStack(Material.BARRIER, 1);
        switch (level_chestplate) {
            case 1 -> chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
            case 2 -> chestplate = new ItemStack(Material.GOLD_CHESTPLATE, 1);
            case 3 -> chestplate = new ItemStack(Material.CHAINMAIL_CHESTPLATE, 1);
            case 4 -> chestplate = new ItemStack(Material.IRON_CHESTPLATE, 1);
            case 5 -> chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        }
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.chestplate.name").replaceAll("&", "§"));
        ArrayList<String> chestplateLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.chestplate.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.chestplate.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.chestplate.lore")) {
                if (lore.equalsIgnoreCase("%enchance%")) {
                    String enchantType = User.getEnchance(p, "chestplate");
                    boolean empty = !EnchanceList.enchances.contains(enchantType);
                    if (empty) User.setEnchance(p, "chestplate", "none");
                    if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            chestplateLores.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    }
                    continue;
                }
                chestplateLores.add(lore
                        .replaceAll("&", "§")
                        .replaceAll("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.chestplate.price")))
                );
            }
            chestplateMeta.setLore(chestplateLores);
        }
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.BARRIER, 1);
        switch (level_leggings) {
            case 1 -> leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
            case 2 -> leggings = new ItemStack(Material.GOLD_LEGGINGS, 1);
            case 3 -> leggings = new ItemStack(Material.CHAINMAIL_LEGGINGS, 1);
            case 4 -> leggings = new ItemStack(Material.IRON_LEGGINGS, 1);
            case 5 -> leggings = new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        }
        ItemMeta leggingsMeta = leggings.getItemMeta();
        leggingsMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.leggings.name").replaceAll("&", "§"));
        ArrayList<String> leggingsLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.leggings.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.leggings.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.leggings.lore")) {
                if (lore.equalsIgnoreCase("%enchance%")) {
                    // 处理 %enchance% 占位符，替换为实际的增强效果
                    String enchantType = User.getEnchance(p, "leggings");
                    boolean empty = !EnchanceList.enchances.contains(enchantType);
                    if (empty) User.setEnchance(p, "leggings", "none");
                    if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            leggingsLores.add(enchance
                                    .replaceAll("&", "§")
                            );
                        }
                    }
                    // 跳过添加占位符本身
                    continue;
                }
                leggingsLores.add(lore
                        .replaceAll("&", "§")
                        .replaceAll("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.leggings.price")))
                );
            }
            leggingsMeta.setLore(leggingsLores);
        }
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.BARRIER, 1);
        switch (level_boots) {
            case 1 -> boots = new ItemStack(Material.LEATHER_BOOTS, 1);
            case 2 -> boots = new ItemStack(Material.GOLD_BOOTS, 1);
            case 3 -> boots = new ItemStack(Material.CHAINMAIL_BOOTS, 1);
            case 4 -> boots = new ItemStack(Material.IRON_BOOTS, 1);
            case 5 -> boots = new ItemStack(Material.DIAMOND_BOOTS, 1);
        }
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.boots.name").replaceAll("&", "§"));
        ArrayList<String> bootsLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.boots.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.boots.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.boots.lore")) {
                if (lore.equalsIgnoreCase("%enchance%")) {
                    // 处理 %enchance% 占位符，替换为实际的增强效果
                    String enchantType = User.getEnchance(p, "boots");
                    boolean empty = !EnchanceList.enchances.contains(enchantType);
                    if (empty) User.setEnchance(p, "boots", "none");
                    if (enchantType != null && EnchanceMaps.enchances.containsKey(enchantType)) {
                        for (String enchance : EnchanceMaps.enchances.get(enchantType)) {
                            bootsLores.add(enchance.replaceAll("&", "§"));
                        }
                    }
                    // 跳过添加占位符本身
                    continue;
                }
                bootsLores.add(lore
                        .replaceAll("&", "§")
                        .replaceAll("%price%", String.valueOf(Yaml.ENCHANCE.getConfig().getInt("menu.items.boots.price")))
                );
            }
            bootsMeta.setLore(bootsLores);
        }
        boots.setItemMeta(bootsMeta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        vaultMeta.setDisplayName(Yaml.ENCHANCE.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vaultLores = new ArrayList<>();
        if (!Yaml.ENCHANCE.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.ENCHANCE.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.ENCHANCE.getConfig().getStringList("menu.items.vault.lore")) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            vaultMeta.setLore(vaultLores);
        }
        vault.setItemMeta(vaultMeta);

        inv.setItem(2, weapon);
        inv.setItem(3, helmet);
        inv.setItem(4, chestplate);
        inv.setItem(5, leggings);
        inv.setItem(6, boots);

        inv.setItem(12, vault);
        inv.setItem(13, close);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "enchance");

    }


}