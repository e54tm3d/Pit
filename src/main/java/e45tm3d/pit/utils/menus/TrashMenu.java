package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
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


        PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(ThePit.setPlaceholderAPI(p, Yaml.TRASH.getConfig().getString("menu.items.close.name").replaceAll("&", "§")))
                .setLore(Yaml.TRASH.getConfig().getStringList("menu.items.close.lore"))
                .setIdentifier("ui_item");

        inv.setItem(13, close.build());

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "trash");

    }

}
