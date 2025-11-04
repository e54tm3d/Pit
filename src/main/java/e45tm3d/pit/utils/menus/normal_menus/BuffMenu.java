package e45tm3d.pit.utils.menus.normal_menus;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.maps.BuffMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class BuffMenu {

    public static String[] buffs = BuffLists.buff.toArray(new String[0]);

    public static void open(Player p) {

        if (User.isPlaying(p)) {

            Inventory inv = Bukkit.createInventory(null, 54, Yaml.BUFF.getConfig().getString("menu.title"));

            p.closeInventory();

            PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER, 1), p)
                    .setName(Yaml.BUFF.getConfig().getString("menu.items.close.name"))
                    .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.close.lore"));

            PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD, 1), p)
                    .setName(Yaml.BUFF.getConfig().getString("menu.items.vault.name"))
                    .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.vault.lore"));

            PlaceholdersItemBuilder slot_1 = User.getEquipBuff(p, "slot_1").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.BUFF.getConfig().getString("menu.items.slot_1.name"))
                            .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.slot_1.lore")) :
                    new PlaceholdersItemBuilder(BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_1")), p);

            PlaceholdersItemBuilder slot_2 = User.getEquipBuff(p, "slot_2").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.BUFF.getConfig().getString("menu.items.slot_2.name"))
                            .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.slot_2.lore")) :
                    new PlaceholdersItemBuilder(BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_2")), p);

            PlaceholdersItemBuilder slot_3 = User.getEquipBuff(p, "slot_3").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.BUFF.getConfig().getString("menu.items.slot_3.name"))
                            .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.slot_3.lore")) :
                    new PlaceholdersItemBuilder(BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_3")), p);

            PlaceholdersItemBuilder slot_4 = User.getEquipBuff(p, "slot_4").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.BUFF.getConfig().getString("menu.items.slot_4.name"))
                            .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.slot_4.lore")) :
                    new PlaceholdersItemBuilder(BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_4")), p);

            PlaceholdersItemBuilder slot_5 = User.getEquipBuff(p, "slot_5").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.BUFF.getConfig().getString("menu.items.slot_5.name"))
                            .setLore(Yaml.BUFF.getConfig().getStringList("menu.items.slot_5.lore")) :
                    new PlaceholdersItemBuilder(BuffMaps.buff_items.get(User.getEquipBuff(p, "slot_5")), p);

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

            inv.setItem(38, slot_1.build());
            inv.setItem(39, slot_2.build());
            inv.setItem(40, slot_3.build());
            inv.setItem(41, slot_4.build());
            inv.setItem(42, slot_5.build());
            inv.setItem(48, vault.build());
            inv.setItem(49, close.build());

            p.openInventory(inv);

            PlayerMaps.menu.put(p.getUniqueId(), "buff");
        }
    }
}