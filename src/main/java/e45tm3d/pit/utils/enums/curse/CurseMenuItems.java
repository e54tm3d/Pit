package e45tm3d.pit.utils.enums.curse;

import e45tm3d.pit.api.enums.Yaml;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum CurseMenuItems {

    DAMAGE_MULTIPLY("damage_multiply"),
    BLOODTHIRSTY("bloodthirsty"),
    BLOODYFIRE("bloodyfire"),
    IMPREGNABLE_FORTRESS("impregnable_fortress"),
    KNOCKBACK_ENCHANCE("knockback_enchance"),
    HATRED("hatred"),
    BLOODYCURSE("bloody_curse");

    String type;

    CurseMenuItems(String type) {
        this.type = type;
    }

    public String getType() {
        return type.toLowerCase();
    }

    public ItemStack getItemStack() {
        try {
            String material = Yaml.CURSE.getConfig().getString("menu.items." + getType() + ".material");
            if (material == null) {
                // 使用默认材质作为后备
                return createDefaultItemStack();
            }

            if (material.contains(":")) {
                String[] parts = material.split(":");
                if (parts.length >= 2) {
                    // 对于Minecraft 1.8.8，DYE材质实际上是INK_SACK
                    String materialName = parts[0];
                    if (materialName.equalsIgnoreCase("DYE")) {
                        materialName = "INK_SACK";
                    }
                    
                    Material mat = Material.getMaterial(materialName);
                    if (mat != null) {
                        try {
                            byte data = Byte.parseByte(parts[1]);
                            ItemStack item = new ItemStack(mat, 1, data);
                            return setupItemMeta(item);
                        } catch (NumberFormatException e) {
                            ItemStack item = new ItemStack(mat);
                            return setupItemMeta(item);
                        }
                    } else {
                        // 材质不存在，使用默认材质
                        return createDefaultItemStack();
                    }
                }
            } else {
                // 对于Minecraft 1.8.8，DYE材质实际上是INK_SACK
                if (material.equalsIgnoreCase("DYE")) {
                    material = "INK_SACK";
                }
                
                Material mat = Material.getMaterial(material);
                if (mat != null) {
                    ItemStack item = new ItemStack(mat, 1);
                    return setupItemMeta(item);
                }
            }
            // 默认返回一个安全的物品
            return createDefaultItemStack();
        } catch (Exception e) {
            // 捕获所有异常并返回默认物品
            e.printStackTrace();
            return createDefaultItemStack();
        }
    }
    
    private ItemStack createDefaultItemStack() {
        ItemStack defaultItem = new ItemStack(Material.STONE);
        try {
            ItemMeta meta = defaultItem.getItemMeta();
            meta.setDisplayName("§cError: Invalid Material");
            defaultItem.setItemMeta(meta);
        } catch (Exception e) {
            // 如果设置meta失败，至少返回一个基本物品
        }
        return defaultItem;
    }
    
    private ItemStack setupItemMeta(ItemStack item) {
        try {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                String displayName = Yaml.CURSE.getConfig().getString("menu.items." + getType() + ".name");
                if (displayName != null) {
                    meta.setDisplayName(displayName.replaceAll("&", "§"));
                }
                
                ArrayList<String> lores = new ArrayList<>();
                if (Yaml.CURSE.getConfig().contains("menu.items." + getType() + ".lore")) {
                    java.util.List<String> loreList = Yaml.CURSE.getConfig().getStringList("menu.items." + getType() + ".lore");
                    if (loreList != null && !loreList.isEmpty()) {
                        for (String lore : loreList) {
                            String price = Yaml.CURSE.getConfig().getString("menu.items." + getType() + ".price");
                            if (price != null) {
                                lores.add(lore.replaceAll("&", "§").replaceAll("%price%", price));
                            } else {
                                lores.add(lore.replaceAll("&", "§"));
                            }
                        }
                        meta.setLore(lores);
                    }
                }
                
                item.setItemMeta(meta);
            }
        } catch (Exception e) {
            // 捕获所有异常但继续返回物品
            e.printStackTrace();
        }
        return item;
    }

    public static CurseMenuItems getItem(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        String str = item.toUpperCase();
        for (CurseMenuItems itemType : CurseMenuItems.values()) {
            if (itemType.getType().equals(str.toLowerCase())) {
                return itemType;
            }
        }
        return null;
    }
}