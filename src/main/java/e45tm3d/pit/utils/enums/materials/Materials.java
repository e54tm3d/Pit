package e45tm3d.pit.utils.enums.materials;

import e45tm3d.pit.api.enums.Yaml;
import e45tm3d.pit.utils.functions.ItemFunction;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Objects;

public enum Materials {

    GOLD_INGOT("gold_ingot"),
    ICE_BLOCK("ice_block"),
    SUPERCONDUCTOR("superconductor"),
    RESENTFUL_HEAD("resentful_head"),
    BONE("bone"),
    SLIME_BALL("slime_ball"),
    PROMETHEAN_FIRE("promethean_fire"),;

    String type;

    Materials(String type) {
        this.type = type;
    }

    public String getType() {
        return type.toLowerCase();
    }

    public ItemStack getItemStack() {
        try {
            String material = Yaml.MATERIAL.getConfig().getString("items." + getType() + ".material");
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

                            if (!Objects.isNull(Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                                for (String keys : Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                                    item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.MATERIAL.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                                }
                            }

                            return setupItemMeta(item);
                        } catch (NumberFormatException e) {
                            ItemStack item = new ItemStack(mat);

                            if (!Objects.isNull(Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                                for (String keys : Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                                    item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.MATERIAL.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
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

                    if (!Objects.isNull(Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes"))) {
                        for (String keys : Yaml.MATERIAL.getConfig().getConfigurationSection("items." + getType() + ".attributes").getKeys(false)) {
                            item = ItemFunction.addAttribute(item, "generic." + keys, Yaml.MATERIAL.getConfig().getDouble("items." + getType() + ".attributes." + keys), 0);
                        }
                    }

                    return setupItemMeta(item);
                }
            }
            // 默认返回一个安全的物品
            return createDefaultItemStack();
        } catch (Exception e) {
            // 捕获所有正常并返回默认物品
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
                String displayName = Yaml.MATERIAL.getConfig().getString("items." + getType() + ".name");
                if (displayName != null) {
                    meta.setDisplayName(displayName.replaceAll("&", "§"));
                }

                ArrayList<String> lores = new ArrayList<>();
                if (Yaml.MATERIAL.getConfig().contains("items." + getType() + ".lore")) {
                    java.util.List<String> loreList = Yaml.MATERIAL.getConfig().getStringList("items." + getType() + ".lore");
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

    public static Materials getItem(String item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        String str = item.toUpperCase();
        for (Materials itemType : Materials.values()) {
            if (itemType.getType().equals(str.toLowerCase())) {
                return itemType;
            }
        }
        return null;
    }
}