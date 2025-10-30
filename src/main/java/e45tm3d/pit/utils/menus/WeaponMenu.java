package e45tm3d.pit.utils.menus;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.ItemBuilder;
import e45tm3d.pit.utils.PlaceholdersItemBuilder;
import e45tm3d.pit.utils.maps.PlayerMaps;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public class WeaponMenu {

    private static final Inventory inv = Bukkit.createInventory(null, 54, Yaml.WEAPON.getConfig().getString("menu.title"));

    public static void open(Player p) {

        p.closeInventory();

        PlaceholdersItemBuilder close = new PlaceholdersItemBuilder(new ItemStack(Material.BARRIER), p)
                .setName(ThePit.setPlaceholderAPI(p, Yaml.WEAPON.getConfig().getString("menu.items.close.name").replaceAll("&", "§")))
                .setLore(Yaml.WEAPON.getConfig().getStringList("menu.items.close.lore"))
                .setIdentifier("ui_item");

        PlaceholdersItemBuilder vault = new PlaceholdersItemBuilder(new ItemStack(Material.EMERALD), p)
                .setName(ThePit.setPlaceholderAPI(p, Yaml.WEAPON.getConfig().getString("menu.items.vault.name").replaceAll("&", "§")))
                .setLore(Yaml.WEAPON.getConfig().getStringList("menu.items.vault.lore"))
                .setIdentifier("ui_item");

        inv.setItem(48, vault.build());
        inv.setItem(49, close.build());

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "weapon");
    }

    public static Inventory getInventory() {
        return inv;
    }

}