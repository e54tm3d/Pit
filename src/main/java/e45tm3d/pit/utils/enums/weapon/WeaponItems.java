package e45tm3d.pit.utils.enums.weapon;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public enum WeaponItems {

    ARTEMIS_BOW("artemis_bow"),
    WOODEN_BOW("wooden_bow"),

    GOLD_AMULET("gold_amulet"),
    EXPERIENCE_AMULET("experience_amulet"),
    MONSTER_RUNE("monster_rune"),

    DIAMOND_SWORD("diamond_sword"),
    GOLDEN_SWORD("golden_sword"),
    ICE_SWORD("ice_sword"),
    LIGHTNING_SWORD("lightning_sword"),
    BONE_SWORD("bone_sword"),
    WOODEN_SWORD("wooden_sword");

    String type;

    WeaponItems(String type) {
        this.type = type;
    }

    public String getType() {
        return type.toLowerCase();
    }

    public ItemStack getItemStack() {
        try {
            String material = Yaml.WEAPON.getConfig().getString("items." + getType() + ".material");
            if (material == null) {
                // 使用默认材质作为后备
                return createDefaultItemStack();
            }

            // 处理自定义头颅
            if (material.equalsIgnoreCase("custom_head")) {
                String base64 = Yaml.WEAPON.getConfig().getString("items." + getType() + ".base64-head");
                if (base64 != null && !base64.isEmpty()) {
                    ItemStack head = ItemFunction.getBase64Head(base64);

                    if (!Objects.isNull(Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                        for (String keys : Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                            head = ItemFunction.addAttribute(head, "generic." + keys, Yaml.WEAPON.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                        }
                    }

                    return setupItemMeta(head);
                }
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

                            if (!Objects.isNull(Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                                for (String keys : Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                                    item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.WEAPON.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                                }
                            }

                            return setupItemMeta(item);
                        } catch (NumberFormatException e) {
                            ItemStack item = new ItemStack(mat);

                            if (!Objects.isNull(Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                                for (String keys : Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                                    item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.WEAPON.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                                }
                            }

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

                    if (!Objects.isNull(Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                        for (String keys : Yaml.WEAPON.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                            item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.WEAPON.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                        }
                    }

                    return setupItemMeta(item);
                }
            }
            // 默认返回一个安全的物品
            return createDefaultItemStack();
        } catch (Exception e) {
            // 捕获所有���常并返回默认物品
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
            // 如果��置meta失败，至少返回一个基本���品
        }
        return defaultItem;
    }
    
    private ItemStack setupItemMeta(ItemStack item) {
        try {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                String displayName = Yaml.WEAPON.getConfig().getString("items." + getType() + ".name");
                if (displayName != null) {
                    meta.setDisplayName(displayName.replaceAll("&", "§"));
                }
                
                ArrayList<String> lores = new ArrayList<>();
                if (Yaml.WEAPON.getConfig().contains("items." + getType() + ".lore")) {
                    java.util.List<String> loreList = Yaml.WEAPON.getConfig().getStringList("items." + getType() + ".lore");
                    if (loreList != null && !loreList.isEmpty()) {
                        for (String loreLine : loreList) {
                            lores.add(loreLine.replaceAll("&", "§"));
                        }
                        meta.setLore(lores);
                    }
                }
                meta.spigot().setUnbreakable(true);
                meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                item.setItemMeta(meta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public static WeaponItems getItem(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        String str = item.toUpperCase();
        for (WeaponItems itemType : WeaponItems.values()) {
            if (itemType.getType().equals(str.toLowerCase())) {
                return itemType;
            }
        }
        return null;
    }
}