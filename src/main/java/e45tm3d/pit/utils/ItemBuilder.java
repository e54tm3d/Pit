package e45tm3d.pit.utils;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.utils.functions.NMSFunction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemBuilder {

    private ItemStack item;

    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    public ItemBuilder setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public ItemBuilder setName(String name) {

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name.replaceAll("&", "§"));

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setLore(List<String> lores) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        if (!lores.isEmpty()) {
            for (String lore : lores) {
                newLore.add(lore.replaceAll("&", "§"));
            }
        }
        meta.setLore(newLore);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder replaceLore(String lore, List<String> replacement) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        for (String lores : meta.getLore()) {
            if (lores.contains(lore)) {
                for (String replace : replacement) {
                    newLore.add(replace.replaceAll("&", "§"));
                }
                continue;
            }
            newLore.add(lores);
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder replaceStringFromLore(String lorePlaceholder, String replacement) {

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return this;
        }

        List<String> originalLore = meta.getLore();
        List<String> newLore = new ArrayList<>();

        if (originalLore == null) {
            meta.setLore(newLore);
            item.setItemMeta(meta);
            return this;
        }

        String processedReplacement = replacement.replace("&", "§");

        String regex = "(?i)" + Pattern.quote(lorePlaceholder);

        for (String line : originalLore) {

            String replacedLine = line.replaceAll(regex, Matcher.quoteReplacement(processedReplacement));

            newLore.add(replacedLine);
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addLore(List<String> lores) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        if (!lores.isEmpty() && meta.hasLore()) {
            newLore.addAll(meta.getLore());
            for (String lore : lores) {
                newLore.add(lore.replaceAll("&", "§"));
            }
        } else if (!lores.isEmpty()) {
            for (String lore : lores) {
                newLore.add(lore.replaceAll("&", "§"));
            }
        }
        meta.setLore(newLore);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setItemFlags(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags(ItemFlag.values());
        meta.addItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setUnbreakable() {

        ItemMeta meta = item.getItemMeta();

        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    public ItemBuilder setIdentifier(String identifier) {

        item = NMSFunction.addNBTTag(item, identifier);
        return this;
    }

    public ItemStack build() {
        return item.clone();
    }
}
