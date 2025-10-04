package e45tm3d.pit.utils.menus.second_menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
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
import java.util.UUID;

public class WeaponUpdateMenu {

    public static void open(Player p, String type) {
        String inventoryTitle = Yaml.WEAPON_UPDATE.getConfig().getString("menu.title");
        if (inventoryTitle == null) {
            inventoryTitle = "Sword Update Menu";
        }
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

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        String closeName = Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.close.name");
        if (closeName == null) {
            closeName = "Close";
        } else {
            closeName = closeName.replaceAll("&", "§");
        }
        closeMeta.setDisplayName(closeName);
        ArrayList<String> closeLores = new ArrayList<>();
        List<String> closeLoreList = Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.close.lore");
        if (closeLoreList != null && !closeLoreList.isEmpty()) {
            for (String lore : closeLoreList) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        // 添加null检查，防止NPE
        String vaultName = Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.vault.name");
        if (vaultName == null) {
            vaultName = "Vault";
        } else {
            vaultName = vaultName.replaceAll("&", "§");
        }
        vaultMeta.setDisplayName(vaultName);
        ArrayList<String> vaultLores = new ArrayList<>();
        List<String> vaultLoreList = Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.vault.lore");
        if (vaultLoreList != null && !vaultLoreList.isEmpty()) {
            for (String lore : vaultLoreList) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            vaultMeta.setLore(vaultLores);
        }
        vault.setItemMeta(vaultMeta);

        ItemStack obtain = new ItemStack(Material.LEATHER, 1);
        ItemMeta obtainMeta = obtain.getItemMeta();
        // 添加null检查，防止NPE
        String obtainName = Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.obtain.name");
        if (obtainName == null) {
            obtainName = "Obtain";
        } else {
            obtainName = obtainName.replaceAll("&", "§");
        }
        obtainMeta.setDisplayName(obtainName);
        ArrayList<String> obtainLores = new ArrayList<>();
        List<String> obtainLoreList = Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.obtain.lore");
        if (obtainLoreList != null && !obtainLoreList.isEmpty()) {
            for (String lore : obtainLoreList) {
                obtainLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            obtainMeta.setLore(obtainLores);
        }
        obtain.setItemMeta(obtainMeta);

        ItemStack upgrade = new ItemStack(Material.EXP_BOTTLE, 1);
        ItemMeta upgradeMeta = upgrade.getItemMeta();
        String upgradeName = Yaml.WEAPON_UPDATE.getConfig().getString("menu.items.upgrade.name");
        if (upgradeName == null) {
            upgradeName = "Upgrade";
        } else {
            upgradeName = upgradeName.replaceAll("&", "§");
        }
        upgradeMeta.setDisplayName(upgradeName);
        ArrayList<String> upgradeLores = new ArrayList<>();
        List<String> upgradeLoreList = Yaml.WEAPON_UPDATE.getConfig().getStringList("menu.items.upgrade.lore");
        if (upgradeLoreList != null && !upgradeLoreList.isEmpty()) {
            for (String lore : upgradeLoreList) {
                upgradeLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                        .replaceAll("%cost_format%", upgradeMsg)

                ));
            }
            upgradeMeta.setLore(upgradeLores);
        }
        upgrade.setItemMeta(upgradeMeta);

        for (int i = 1; i <= 4; i++) {
            ItemStack progress = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 7);
            if (i <= level) progress = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 5);
            ItemMeta progressMeta = progress.getItemMeta();
            String displayName = WeaponMaps.getTierName(type, i);
            if (displayName == null) {
                displayName = "Unknown Tier " + i;
            } else {
                displayName = displayName
                        .replaceAll("&", "§")
                        .replaceAll("%tier%", String.valueOf(i));
            }
            progressMeta.setDisplayName(displayName);
            ArrayList<String> progressLores = new ArrayList<>();
            List<String> tierLoreList = WeaponMaps.getTierLore(type, i);
            if (tierLoreList != null && !tierLoreList.isEmpty()) {
                for (String lore : tierLoreList) {
                    progressLores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                progressMeta.setLore(progressLores);
            }
            progress.setItemMeta(progressMeta);
            inv.setItem(11 + i, progress);
        }

        inv.setItem(31, close);
        inv.setItem(30, vault);
        inv.setItem(27, obtain);
        inv.setItem(11, upgrade);

        p.openInventory(inv);

        PlayerMaps.menu.put(uuid, "weapon_update_" + type);
    }
}