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
import java.util.Objects;

public class WeaponMenu {

    private static final Inventory inv = Bukkit.createInventory(null, 54, Yaml.WEAPON.getConfig().getString("menu.title"));

    public static void open(Player p) {

        p.closeInventory();

        ItemStack close = new ItemStack(Material.BARRIER, 1);
        ItemMeta closeMeta = close.getItemMeta();
        closeMeta.setDisplayName(Yaml.WEAPON.getConfig().getString("menu.items.close.name").replaceAll("&", "§"));
        ArrayList<String> closeLores = new ArrayList<>();
        if (!Yaml.WEAPON.getConfig().getStringList("menu.items.close.lore").isEmpty()
                || !Objects.isNull(Yaml.WEAPON.getConfig().getStringList("menu.items.close.lore"))) {
            for (String lore : Yaml.WEAPON.getConfig().getStringList("menu.items.close.lore")) {
                closeLores.add(lore.replaceAll("&", "§"));
            }
            closeMeta.setLore(closeLores);
        }
        close.setItemMeta(closeMeta);

        ItemStack vault = new ItemStack(Material.EMERALD, 1);
        ItemMeta vaultMeta = vault.getItemMeta();
        vaultMeta.setDisplayName(Yaml.WEAPON.getConfig().getString("menu.items.vault.name").replaceAll("&", "§"));
        ArrayList<String> vaultLores = new ArrayList<>();
        if (!Yaml.WEAPON.getConfig().getStringList("menu.items.vault.lore").isEmpty()
                || !Objects.isNull(Yaml.WEAPON.getConfig().getStringList("menu.items.vault.lore"))) {
            for (String lore : Yaml.WEAPON.getConfig().getStringList("menu.items.vault.lore")) {
                vaultLores.add(ThePit.setPlaceholderAPI(p, lore
                        .replaceAll("&", "§")
                ));
            }
            vaultMeta.setLore(vaultLores);
        }
        vault.setItemMeta(vaultMeta);

        inv.setItem(48, vault);
        inv.setItem(49, close);

        p.openInventory(inv);

        PlayerMaps.menu.put(p.getUniqueId(), "weapon");
    }

    public static Inventory getInventory() {
        return inv;
    }

}