package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.lists.EnchanceList;
import e45tm3d.pit.utils.maps.CurseMaps;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CurseMenu {

    public static String[] curse = CurseLists.curse.toArray(new String[0]);

    public static void open(Player p) {

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 54, Yaml.CURSE.getConfig().getString("menu.title"));

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.CURSE.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.CURSE.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.CURSE.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.CURSE.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        vaultMeta.setDisplayName(Yaml.CURSE.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vaultLores = new ArrayList<>();
        if (!Yaml.CURSE.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.CURSE.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.CURSE.getConfig().getStringList("menu.items.vault.lore")) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            vaultMeta.setLore(vaultLores);
        }
        vault.setItemMeta(vaultMeta);

        ItemStack slot_1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        String equippedCurse = User.getEquipCurse(p, "slot_1");
        if (equippedCurse.equalsIgnoreCase("none")) {
            ItemMeta slot_1Meta = slot_1.getItemMeta();
            slot_1Meta.setDisplayName(Yaml.CURSE.getConfig().getString("menu.items.slot_1.name").replaceAll("&", "§"));
            ArrayList<String> slot_1Lores = new ArrayList<>();
            if (!Yaml.CURSE.getConfig().getStringList("menu.items.slot_1.lore").isEmpty()
                    || !Objects.isNull(Yaml.CURSE.getConfig().getStringList("menu.items.slot_1.lore"))) {
                for (String lore : Yaml.CURSE.getConfig().getStringList("menu.items.slot_1.lore")) {
                    slot_1Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_1Meta.setLore(slot_1Lores);
            }
            slot_1.setItemMeta(slot_1Meta);
        } else {
            slot_1 = CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_1"));
        }

        ItemStack slot_2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipCurse(p, "slot_2").equalsIgnoreCase("none")) {
            ItemMeta slot_2Meta = slot_2.getItemMeta();
            slot_2Meta.setDisplayName(Yaml.CURSE.getConfig().getString("menu.items.slot_2.name").replaceAll("&", "§"));
            ArrayList<String> slot_2Lores = new ArrayList<>();
            if (!Yaml.CURSE.getConfig().getStringList("menu.items.slot_2.lore").isEmpty()
                    || !Objects.isNull(Yaml.CURSE.getConfig().getStringList("menu.items.slot_2.lore"))) {
                for (String lore : Yaml.CURSE.getConfig().getStringList("menu.items.slot_2.lore")) {
                    slot_2Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_2Meta.setLore(slot_2Lores);
            }
            slot_2.setItemMeta(slot_2Meta);
        } else {
            slot_2 = CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_2"));
        }

        ItemStack slot_3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipCurse(p, "slot_3").equalsIgnoreCase("none")) {
            ItemMeta slot_3Meta = slot_3.getItemMeta();
            slot_3Meta.setDisplayName(Yaml.CURSE.getConfig().getString("menu.items.slot_3.name").replaceAll("&", "§"));
            ArrayList<String> slot_3Lores = new ArrayList<>();
            if (!Yaml.CURSE.getConfig().getStringList("menu.items.slot_3.lore").isEmpty()
                    || !Objects.isNull(Yaml.CURSE.getConfig().getStringList("menu.items.slot_3.lore"))) {
                for (String lore : Yaml.CURSE.getConfig().getStringList("menu.items.slot_3.lore")) {
                    slot_3Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_3Meta.setLore(slot_3Lores);
            }
            slot_3.setItemMeta(slot_3Meta);
        } else {
            slot_3 = CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_3"));
        }

        for (int i = 0; i < Math.min(44, CurseLists.curse.size()); i++) {

            boolean unlocked = User.getCurseStat(p, CurseLists.curse.get(i));

            ItemStack item = new ItemStack(CurseMaps.curse_menu_items.get(CurseLists.curse.get(i)));
            ItemMeta meta = item.getItemMeta();

            String curse = CurseLists.curse.get(i);

            ArrayList<String> lores = new ArrayList<>();
            if (!meta.getLore().isEmpty() || !Objects.isNull(meta.getLore())) {
                for (String lore : meta.getLore()) {
                    if (lore.equalsIgnoreCase("%cost_format%")) {
                        if (unlocked) {
                            for (String text : CurseMaps.unlock.get(curse)) {
                                lores.add(text
                                        .replaceAll("&", "§")
                                        .replaceAll("%price%", String.valueOf(CurseMaps.price.get(CurseLists.curse.get(i))))
                                );
                            }
                        } else {
                            for (String text : CurseMaps.lock.get(curse)) {
                                lores.add(text
                                        .replaceAll("&", "§")
                                        .replaceAll("%price%", String.valueOf(CurseMaps.price.get(CurseLists.curse.get(i))))
                                );
                            }
                        }
                        continue;
                    }
                    lores.add(lore.replaceAll("&", "§"));
                }
                meta.setLore(lores);
                item.setItemMeta(meta);
            }
            inv.setItem(i, item);
        }

        inv.setItem(39, slot_1);
        inv.setItem(40, slot_2);
        inv.setItem(41, slot_3);
        inv.setItem(48, vault);
        inv.setItem(49, close);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "curse");
    }
}