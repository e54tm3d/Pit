package e45tm3d.pit.utils.menus;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Objects;

public class TrashMenu {

    public static void open(Player p) {

        p.closeInventory();

        Inventory inv = Bukkit.createInventory(null, 18, Yaml.TRASH.getConfig().getString("menu.title"));

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.TRASH.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.TRASH.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.TRASH.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.TRASH.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        inv.setItem(13, close);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "trash");

    }

}
