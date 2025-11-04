package e45tm3d.pit.utils.menus.normal_menus;

import e45tm3d.pit.api.User;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.lists.CurseLists;
import e45tm3d.pit.utils.maps.CurseMaps;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class CurseMenu {

    public static String[] curse = CurseLists.curse.toArray(new String[0]);

    public static void open(Player p) {

        if (User.isPlaying(p)) {

            p.closeInventory();

            Inventory inv = Bukkit.createInventory(null, 54, Yaml.CURSE.getConfig().getString("menu.title"));

            PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                    .setName(Yaml.CURSE.getConfig().getString("menu.items.close.name"))
                    .setLore(Yaml.CURSE.getConfig().getStringList("menu.items.close.lore"));

            PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD), p)
                    .setName(Yaml.CURSE.getConfig().getString("menu.items.vault.name"))
                    .setLore(Yaml.CURSE.getConfig().getStringList("menu.items.vault.lore"));

            PlaceholdersItemBuilder slot_1 = User.getEquipCurse(p, "slot_1").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.CURSE.getConfig().getString("menu.items.slot_1.name"))
                            .setLore(Yaml.CURSE.getConfig().getStringList("menu.items.slot_1.lore")) :
                    new PlaceholdersItemBuilder(CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_1")), p);

            PlaceholdersItemBuilder slot_2 = User.getEquipCurse(p, "slot_2").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.CURSE.getConfig().getString("menu.items.slot_2.name"))
                            .setLore(Yaml.CURSE.getConfig().getStringList("menu.items.slot_2.lore")) :
                    new PlaceholdersItemBuilder(CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_2")), p);

            PlaceholdersItemBuilder slot_3 = User.getEquipCurse(p, "slot_3").equalsIgnoreCase("none") ?
                    new PlaceholdersItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14), p)
                            .setName(Yaml.CURSE.getConfig().getString("menu.items.slot_3.name"))
                            .setLore(Yaml.CURSE.getConfig().getStringList("menu.items.slot_3.lore")) :
                    new PlaceholdersItemBuilder(CurseMaps.curse_items.get(User.getEquipCurse(p, "slot_3")), p);

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

            inv.setItem(39, slot_1.build());
            inv.setItem(40, slot_2.build());
            inv.setItem(41, slot_3.build());
            inv.setItem(48, vault.build());
            inv.setItem(49, close.build());

            p.openInventory(inv);

            PlayerMaps.menu.put(p.getUniqueId(), "curse");
        }
    }
}