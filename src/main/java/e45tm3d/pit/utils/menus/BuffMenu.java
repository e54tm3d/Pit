package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.maps.BuffMaps;
import e45tm3d.pit.utils.maps.CurseMaps;
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
import java.util.Objects;

public class BuffMenu {

    public static String[] buffs = BuffLists.buff.toArray(new String[0]);

    public static void open(Player p) {

        Inventory inv = Bukkit.createInventory(null, 54, Yaml.BUFF.getConfig().getString("menu.title"));

        p.closeInventory();

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.BUFF.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        vaultMeta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vaultLores = new ArrayList<>();
        if (!Yaml.BUFF.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.vault.lore")) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            vaultMeta.setLore(vaultLores);
        }
        vault.setItemMeta(vaultMeta);

        ItemStack slot_1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        String equippedBuff = User.getEquipBuff(p, "slot_1");
        if (equippedBuff.equalsIgnoreCase("none")) {
            ItemMeta slot_1Meta = slot_1.getItemMeta();
            slot_1Meta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.slot_1.name").replaceAll("&", "§"));
            ArrayList<String> slot_1Lores = new ArrayList<>();
            if (!Yaml.BUFF.getConfig().getStringList("menu.items.slot_1.lore").isEmpty()
                    || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.slot_1.lore"))) {
                for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.slot_1.lore")) {
                    slot_1Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_1Meta.setLore(slot_1Lores);
            }
            slot_1.setItemMeta(slot_1Meta);
        } else {
            slot_1 = BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_1"));
        }

        ItemStack slot_2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipBuff(p, "slot_2").equalsIgnoreCase("none")) {
            ItemMeta slot_2Meta = slot_2.getItemMeta();
            slot_2Meta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.slot_2.name").replaceAll("&", "§"));
            ArrayList<String> slot_2Lores = new ArrayList<>();
            if (!Yaml.BUFF.getConfig().getStringList("menu.items.slot_2.lore").isEmpty()
                    || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.slot_2.lore"))) {
                for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.slot_2.lore")) {
                    slot_2Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_2Meta.setLore(slot_2Lores);
            }
            slot_2.setItemMeta(slot_2Meta);
        } else {
            slot_2 = BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_2"));
        }

        ItemStack slot_3 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipBuff(p, "slot_3").equalsIgnoreCase("none")) {
            ItemMeta slot_3Meta = slot_3.getItemMeta();
            slot_3Meta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.slot_3.name").replaceAll("&", "§"));
            ArrayList<String> slot_3Lores = new ArrayList<>();
            if (!Yaml.BUFF.getConfig().getStringList("menu.items.slot_3.lore").isEmpty()
                    || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.slot_3.lore"))) {
                for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.slot_3.lore")) {
                    slot_3Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_3Meta.setLore(slot_3Lores);
            }
            slot_3.setItemMeta(slot_3Meta);
        } else {
            slot_3 = BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_3"));
        }

        ItemStack slot_4 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipBuff(p, "slot_4").equalsIgnoreCase("none")) {
            ItemMeta slot_4Meta = slot_4.getItemMeta();
            slot_4Meta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.slot_4.name").replaceAll("&", "§"));
            ArrayList<String> slot_4Lores = new ArrayList<>();
            if (!Yaml.BUFF.getConfig().getStringList("menu.items.slot_4.lore").isEmpty()
                    || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.slot_4.lore"))) {
                for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.slot_4.lore")) {
                    slot_4Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_4Meta.setLore(slot_4Lores);
            }
            slot_4.setItemMeta(slot_4Meta);
        } else {
            slot_4 = BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_4"));
        }

        ItemStack slot_5 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
        if (User.getEquipBuff(p, "slot_5").equalsIgnoreCase("none")) {
            ItemMeta slot_5Meta = slot_5.getItemMeta();
            slot_5Meta.setDisplayName(Yaml.BUFF.getConfig().getString("menu.items.slot_5.name").replaceAll("&", "§"));
            ArrayList<String> slot_5Lores = new ArrayList<>();
            if (!Yaml.BUFF.getConfig().getStringList("menu.items.slot_5.lore").isEmpty()
                    || !Objects.isNull(Yaml.BUFF.getConfig().getStringList("menu.items.slot_5.lore"))) {
                for (String lore : Yaml.BUFF.getConfig().getStringList("menu.items.slot_5.lore")) {
                    slot_5Lores.add(ThePit.setPlaceholderAPI(p, lore
                            .replaceAll("&", "§")
                    ));
                }
                slot_5Meta.setLore(slot_5Lores);
            }
            slot_5.setItemMeta(slot_5Meta);
        } else {
            slot_5 = BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_5"));
        }

        for (int i = 0; i < Math.min(44, BuffLists.buff.size()); i++) {

            boolean unlocked = User.getBuffStat(p, BuffLists.buff.get(i));

            ItemStack item = new ItemStack(BuffMaps.buff_menu_items.get(BuffLists.buff.get(i)));
            ItemMeta meta = item.getItemMeta();

            String buff = BuffLists.buff.get(i);

            ArrayList<String> lores = new ArrayList<>();
            if (meta.getLore() != null && !meta.getLore().isEmpty()) {
                for (String lore : meta.getLore()) {
                    if (lore.equalsIgnoreCase("%cost_format%")) {
                        if (unlocked) {
                            for (String text : BuffMaps.unlock.get(buff)) {
                                lores.add(text
                                        .replaceAll("&", "§")
                                        .replaceAll("%price%", String.valueOf(BuffMaps.price.get(buff)))
                                );
                            }
                        } else {
                            for (String text : BuffMaps.lock.get(buff)) {
                                lores.add(text
                                        .replaceAll("&", "§")
                                        .replaceAll("%price%", String.valueOf(BuffMaps.price.get(buff)))
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

        inv.setItem(38, slot_1);
        inv.setItem(39, slot_2);
        inv.setItem(40, slot_3);
        inv.setItem(41, slot_4);
        inv.setItem(42, slot_5);
        inv.setItem(48, vault);
        inv.setItem(49, close);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "buff");
    }

}