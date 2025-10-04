package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
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

        ItemStack locked = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
        ItemMeta lockedMeta = locked.getItemMeta();
        lockedMeta.setDisplayName("§7");
        locked.setItemMeta(lockedMeta);

        ItemStack unlocked = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
        ItemMeta unlockedMeta = unlocked.getItemMeta();
        unlockedMeta.setDisplayName("§7");
        unlocked.setItemMeta(unlockedMeta);

        inv.setItem(11, locked);
        inv.setItem(12, locked);
        inv.setItem(13, locked);
        inv.setItem(14, locked);
        inv.setItem(15, locked);
        inv.setItem(20, locked);
        inv.setItem(21, locked);
        inv.setItem(22, locked);
        inv.setItem(23, locked);
        inv.setItem(24, locked);
        inv.setItem(29, locked);
        inv.setItem(30, locked);
        inv.setItem(31, locked);
        inv.setItem(32, locked);
        inv.setItem(33, locked);
        inv.setItem(38, locked);
        inv.setItem(39, locked);
        inv.setItem(40, locked);
        inv.setItem(41, locked);
        inv.setItem(42, locked);

        ItemStack armorCharacteristics = new ItemStack(Material.PAPER, 1);
        ItemMeta ameta = armorCharacteristics.getItemMeta();
        ameta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.armor_characteristics.name").replaceAll("&", "§"));
        ArrayList<String> armorCharacteristicsLores = new ArrayList<>();
        for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.armor_characteristics.lore")) {
            armorCharacteristicsLores.add(lore.replaceAll("&", "§"));
        }
        ameta.setLore(armorCharacteristicsLores);
        armorCharacteristics.setItemMeta(ameta);

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        vaultMeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vaultLores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.vault.lore")) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore.replaceAll("&", "§")));
            }
            vaultMeta.setLore(vaultLores);
            vault.setItemMeta(vaultMeta);
        }

        ItemStack helmet = new ItemStack(Material.BARRIER);
        ItemMeta hmeta = helmet.getItemMeta();
        switch (level_helmet) {
            case 1 -> helmet.setType(Material.LEATHER_HELMET);
            case 2 -> helmet.setType(Material.GOLD_HELMET);
            case 3 -> helmet.setType(Material.CHAINMAIL_HELMET);
            case 4 -> helmet.setType(Material.IRON_HELMET);
            case 5 -> helmet.setType(Material.DIAMOND_HELMET);
        }
        hmeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.helmet.name").replaceAll("&", "§"));
        ArrayList<String> lores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.helmet.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.helmet.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.helmet.lore")) {
                lores.add(lore.replaceAll("&", "§")
                        .replaceAll("%level%", String.valueOf(level_helmet))
                        .replaceAll("%cost_format%", helmet_level));
            }
            hmeta.setLore(lores);
        }
        helmet.setItemMeta(hmeta);

        inv.setItem(10, helmet);

        if (level_helmet >= 1) inv.setItem(11, unlocked);
        if (level_helmet >= 2) inv.setItem(12, unlocked);
        if (level_helmet >= 3) inv.setItem(13, unlocked);
        if (level_helmet >= 4) inv.setItem(14, unlocked);
        if (level_helmet >= 5) inv.setItem(15, unlocked);

        ItemStack chestplate = new ItemStack(Material.BARRIER);
        ItemMeta cmeta = chestplate.getItemMeta();
        switch (level_chestplate) {
            case 1 -> chestplate.setType(Material.LEATHER_CHESTPLATE);
            case 2 -> chestplate.setType(Material.GOLD_CHESTPLATE);
            case 3 -> chestplate.setType(Material.CHAINMAIL_CHESTPLATE);
            case 4 -> chestplate.setType(Material.IRON_CHESTPLATE);
            case 5 -> chestplate.setType(Material.DIAMOND_CHESTPLATE);
        }
        cmeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.chestplate.name").replaceAll("&", "§"));
        ArrayList<String> clores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.chestplate.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.chestplate.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.chestplate.lore")) {
                clores.add(lore.replaceAll("&", "§")
                        .replaceAll("%level%", String.valueOf(level_chestplate))
                        .replaceAll("%cost_format%", chestplate_level));
            }
            cmeta.setLore(clores);
        }
        chestplate.setItemMeta(cmeta);

        inv.setItem(19, chestplate);

        if (level_chestplate >= 1) inv.setItem(20, unlocked);
        if (level_chestplate >= 2) inv.setItem(21, unlocked);
        if (level_chestplate >= 3) inv.setItem(22, unlocked);
        if (level_chestplate >= 4) inv.setItem(23, unlocked);
        if (level_chestplate >= 5) inv.setItem(24, unlocked);

        ItemStack leggings = new ItemStack(Material.BARRIER);
        ItemMeta lmeta = leggings.getItemMeta();
        switch (level_leggings) {
            case 1 -> leggings.setType(Material.LEATHER_LEGGINGS);
            case 2 -> leggings.setType(Material.GOLD_LEGGINGS);
            case 3 -> leggings.setType(Material.CHAINMAIL_LEGGINGS);
            case 4 -> leggings.setType(Material.IRON_LEGGINGS);
            case 5 -> leggings.setType(Material.DIAMOND_LEGGINGS);
        }
        lmeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.leggings.name").replaceAll("&", "§"));
        ArrayList<String> llores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.leggings.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.leggings.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.leggings.lore")) {
                llores.add(lore.replaceAll("&", "§")
                        .replaceAll("%level%", String.valueOf(level_leggings))
                        .replaceAll("%cost_format%", leggings_level));
            }
            lmeta.setLore(llores);
        }
        leggings.setItemMeta(lmeta);

        inv.setItem(28, leggings);

        if (level_leggings >= 1) inv.setItem(29, unlocked);
        if (level_leggings >= 2) inv.setItem(30, unlocked);
        if (level_leggings >= 3) inv.setItem(31, unlocked);
        if (level_leggings >= 4) inv.setItem(32, unlocked);
        if (level_leggings >= 5) inv.setItem(33, unlocked);

        ItemStack boots = new ItemStack(Material.BARRIER);
        ItemMeta bmeta = boots.getItemMeta();
        switch (level_boots) {
            case 1 -> boots.setType(Material.LEATHER_BOOTS);
            case 2 -> boots.setType(Material.GOLD_BOOTS);
            case 3 -> boots.setType(Material.CHAINMAIL_BOOTS);
            case 4 -> boots.setType(Material.IRON_BOOTS);
            case 5 -> boots.setType(Material.DIAMOND_BOOTS);
        }
        bmeta.setDisplayName(Yaml.ARMOR.getConfig().getString("menu.items.boots.name").replaceAll("&", "§"));
        ArrayList<String> blores = new ArrayList<>();
        if (!Yaml.ARMOR.getConfig().getStringList("menu.items.boots.lore").isEmpty()
                || !Objects.isNull(Yaml.ARMOR.getConfig().getStringList("menu.items.boots.lore"))) {
            for (String lore : Yaml.ARMOR.getConfig().getStringList("menu.items.boots.lore")) {
                blores.add(lore.replaceAll("&", "§")
                        .replaceAll("%level%", String.valueOf(level_boots))
                        .replaceAll("%cost_format%", boots_level));
            }
            bmeta.setLore(blores);
        }
        boots.setItemMeta(bmeta);

        inv.setItem(37, boots);

        if (level_boots >= 1) inv.setItem(38, unlocked);
        if (level_boots >= 2) inv.setItem(39, unlocked);
        if (level_boots >= 3) inv.setItem(40, unlocked);
        if (level_boots >= 4) inv.setItem(41, unlocked);
        if (level_boots >= 5) inv.setItem(42, unlocked);

        inv.setItem(16, armorCharacteristics);
        inv.setItem(25, armorCharacteristics);
        inv.setItem(34, armorCharacteristics);
        inv.setItem(43, armorCharacteristics);
        inv.setItem(49, close);
        inv.setItem(48, vault);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "armor");
    }
}