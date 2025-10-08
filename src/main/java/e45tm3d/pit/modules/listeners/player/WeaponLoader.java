package e45tm3d.pit.modules.listeners.player;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.User;
import e45tm3d.pit.api.events.PlayerEnchanceEvent;
import e45tm3d.pit.modules.enchance.EnchanceType;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.ItemFunction;
import e45tm3d.pit.utils.lists.BuffLists;
import e45tm3d.pit.utils.lists.ItemLists;
import e45tm3d.pit.utils.maps.EnchanceMaps;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeaponLoader extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerJoinEvent e) {
            updateLore(e.getPlayer());
        } else if (event instanceof PlayerEnchanceEvent e) {
            Bukkit.getScheduler().runTaskLater(ThePit.getInstance(), () -> {
                updateLore(e.getPlayer());
            }, 10);
        } else if (event instanceof PlayerPickupItemEvent e) {
            Player player = e.getPlayer();
            ItemStack item = e.getItem().getItemStack();
            updatePickupItemLore(player, item);
        }
    }

    private void updateLore(Player p) {
        ItemStack[] contents = p.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            ItemStack item = contents[i];
            if (item == null) continue;
            for (String weapons : ItemLists.weapons) {
                if (ItemFunction.hasNBTTag(item, weapons)) {
                    ItemMeta meta = ItemFunction.searchItem(weapons).getItemMeta();
                    if (meta == null) continue;
                    List<String> normal = meta.getLore();
                    if (normal == null) normal = new ArrayList<>();
                    String enchancement = User.getEnchance(p, "weapon");
                    List<String> enchance = EnchanceMaps.enchances.get(enchancement);
                    if (enchance != null && !enchancement.equals("none")) {
                        normal.add("");
                        for (String enchances : enchance) {
                            normal.add(enchances.replaceAll("&", "§"));
                            if (!item.getItemMeta().getLore().equals(normal)) {
                                meta.setLore(normal);
                                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                item.setItemMeta(meta);
                                contents[i] = item;
                            }
                        }
                    } else if (enchancement.equals("none")) {
                        meta.removeEnchant(Enchantment.DURABILITY);
                        item.setItemMeta(meta);
                    }
                }
            }
        }
        p.getInventory().setContents(contents);
    }

    private void updatePickupItemLore(Player p, ItemStack item) {
        if (item == null) return;
        for (String weapons : ItemLists.weapons) {
            if (ItemFunction.hasNBTTag(item, weapons)) {
                ItemMeta meta = ItemFunction.searchItem(weapons).getItemMeta();
                if (meta == null) continue;
                List<String> normal = meta.getLore();
                if (normal == null) normal = new ArrayList<>();
                String enchancement = User.getEnchance(p, "weapon");
                List<String> enchance = EnchanceMaps.enchances.get(enchancement);
                if (enchance != null && !enchancement.equals("none")) {
                    normal.add("");
                    for (String enchances : enchance) {
                        normal.add(enchances.replaceAll("&", "§"));
                    }
                    meta.setLore(normal);
                    meta.addEnchant(Enchantment.DURABILITY, 1, false);
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    item.setItemMeta(meta);
                } else if (enchancement.equals("none")) {
                    meta.removeEnchant(Enchantment.DURABILITY);
                    item.setItemMeta(meta);
                }
            }
        }
    }
}