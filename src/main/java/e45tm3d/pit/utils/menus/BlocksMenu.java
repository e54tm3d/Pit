package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlocksMenu {

    public static String[] blocks = Yaml.BLOCKS.getConfig().getConfigurationSection("menu.custom_items").getKeys(false).toArray(new String[0]);

    public static void open(Player p) {

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 54, Yaml.BLOCKS.getConfig().getString("menu.title"));

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta close_meta = close.getItemMeta();
        close_meta.setDisplayName(Yaml.BLOCKS.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> close_lores = new ArrayList<>();
        if (!Yaml.BLOCKS.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.BLOCKS.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.BLOCKS.getConfig().getStringList("menu.items.close.lore")) {
                close_lores.add(lore.replaceAll("&", "§"));
            }
            close_meta.setLore(close_lores);
        }
        close.setItemMeta(close_meta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vault_meta = vault.getItemMeta();
        vault_meta.setDisplayName(Yaml.BLOCKS.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vault_lores = new ArrayList<>();
        if (!Yaml.BLOCKS.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.BLOCKS.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.BLOCKS.getConfig().getStringList("menu.items.vault.lore")) {
                vault_lores.add(ThePit.setPlaceholderAPI(p, lore.replaceAll("&", "§")));

            }
            vault_meta.setLore(vault_lores);
        }
        vault.setItemMeta(vault_meta);


        for (int i = 0; i < Math.min(44, blocks.length); i++) {
            String material = Yaml.BLOCKS.getConfig().getString("menu.custom_items." + blocks[i] + ".material");
            if (material != null && material.contains(":")) {
                String[] parts = material.split(":");
                ItemStack item = new ItemStack(Material.getMaterial(parts[0]), 16, Byte.parseByte(parts[1]));
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Yaml.BLOCKS.getConfig().getString("menu.custom_items." + blocks[i] + ".name").replaceAll("&", "§"));
                ArrayList<String> lores = new ArrayList<>();
                if (!Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore").isEmpty()
                        || !Objects.isNull(Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore"))) {
                    for (String lore : Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore")) {
                        lores.add(lore.replaceAll("&", "§")
                                .replaceAll("%price%", Yaml.BLOCKS.getConfig().getString("menu.custom_items." + blocks[i] + ".price")));
                    }
                    meta.setLore(lores);
                }
                item.setItemMeta(meta);

                inv.setItem(i, item);

            } else if (material != null) {
                ItemStack item = new ItemStack(Material.getMaterial(material), 16);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(Yaml.BLOCKS.getConfig().getString("menu.custom_items." + blocks[i] + ".name").replaceAll("&", "§"));
                ArrayList<String> lores = new ArrayList<>();
                if (!Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore").isEmpty()
                        || !Objects.isNull(Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore"))) {
                    for (String lore : Yaml.BLOCKS.getConfig().getStringList("menu.custom_items." + blocks[i] + ".lore")) {
                        lores.add(lore.replaceAll("&", "§")
                                .replaceAll("%price%", Yaml.BLOCKS.getConfig().getString("menu.custom_items." + blocks[i] + ".price")));
                    }
                    meta.setLore(lores);
                }
                item.setItemMeta(meta);

                inv.setItem(i, item);
            }
        }

        inv.setItem(49, close);
        inv.setItem(48, vault);
        p.openInventory(inv);
        PlayerMaps.menu.put(p.getUniqueId(), "blocks");
    }
}