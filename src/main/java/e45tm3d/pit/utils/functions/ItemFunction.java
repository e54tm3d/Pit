package e45tm3d.pit.utils.functions;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import e45tm3d.pit.utils.lists.ItemLists;
import e45tm3d.pit.utils.maps.MaterialMaps;
import e45tm3d.pit.utils.maps.WeaponMaps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.*;

public class ItemFunction {

    public static ItemStack addAttribute(ItemStack item, String attributeName, double amount, int operation) {
        return NMSFunction.addAttribute(item, attributeName, amount, operation);
    }

    public static ItemStack addNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.addNBTTag(item, nbtTagName);
    }

    public static boolean hasNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.hasNBTTag(item, nbtTagName);
    }

    public static ItemStack removeNBTTag(ItemStack item, String nbtTagName) {
        return NMSFunction.removeNBTTag(item, nbtTagName);
    }

    public static void spawnItemExplosion(Location location, org.bukkit.inventory.ItemStack item, int amount, int radius, int speed) {
        NMSFunction.spawnItemExplosion(location, item, amount, radius, speed);
    }

    public static void registerMaterial(String item, ItemStack itemStack) {
        ItemLists.materials.add(item.toLowerCase());
        MaterialMaps.material_items.put(item.toLowerCase(), itemStack);
    }

    public static List<String> searchWeapons() {
        return ItemLists.weapons;
    }

    public static List<String> searchMaterials() {
        return ItemLists.materials;
    }

    public static List<String> searchAmulets() {
        return ItemLists.amulets;
    }

    public static List<String> searchAllItems() {
        List<String> items = new ArrayList<>();
        items.addAll(ItemLists.materials);
        items.addAll(ItemLists.weapons);
        items.addAll(ItemLists.amulets);
        return items;
    }

    public static ItemStack searchMaterial(String str) {
        return MaterialMaps.material_items.get(str);
    }

    public static ItemStack searchWeapons(String str) {
        return WeaponMaps.weapon_items.get(str);
    }

    public static ItemStack searchItem(String item) {

        Map<String, ItemStack> allItems = new HashMap<>();
        allItems.putAll(MaterialMaps.material_items);
        allItems.putAll(WeaponMaps.weapon_items);

        return allItems.getOrDefault(item, null);
    }

    public static boolean hasItemAtLease(Player p, int amount, String item) {
        int i = 0;
        for (ItemStack items : p.getInventory().getContents()) {
            if (isItem(items, item)) {
                i += items.getAmount();
            }
        }
        return i >= amount;
    }

    public static void consumeItem(Player p, int amount, String item) {
        int remaining = amount;
        Inventory inv = p.getInventory();
        ItemStack[] contents = inv.getContents().clone();

        for (int i = 0; i < contents.length; i++) {
            ItemStack stack = contents[i];
            if (stack == null || !isItem(stack, item)) {
                continue;
            }

            int stackSize = stack.getAmount();
            if (stackSize <= remaining) {
                remaining -= stackSize;
                inv.setItem(i, null);
            } else {
                stack.setAmount(stackSize - remaining);
                inv.setItem(i, stack);
                remaining = 0;
            }

            if (remaining == 0) {
                break;
            }
        }
        p.updateInventory();
    }

    public static boolean isItem(ItemStack items, String item) {
        return ItemFunction.hasNBTTag(items, item.toLowerCase());
    }

    public static ItemStack getBase64Head(String value) {

        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");

        profile.getProperties().put("textures", new Property("textures", value));

        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

    public static ItemStack getNameHead(String name) {

        OfflinePlayer thePlayer = Bukkit.getOfflinePlayer(name);
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwner(thePlayer.getName());
        head.setItemMeta(meta);

        return head;
    }
}