package e45tm3d.pit.utils;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.utils.functions.NMSFunction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholdersItemBuilder {

    private ItemStack item;
    private final Player p;

    public PlaceholdersItemBuilder(ItemStack item, Player p) {
        this.p = p;
        this.item = item;
    }

    public PlaceholdersItemBuilder setMaterial(Material material) {
        item.setType(material);
        return this;
    }

    public PlaceholdersItemBuilder setName(String name) {

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ThePit.setPlaceholderAPI(p, name.replaceAll("&", "§")));

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder setLore(List<String> lores) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        if (!lores.isEmpty()) {
            for (String lore : lores) {
                newLore.add(ThePit.setPlaceholderAPI(p, lore.replaceAll("&", "§")));
            }
        }
        meta.setLore(newLore);

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder replaceLore(String lore, List<String> replacement) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        for (String lores : meta.getLore()) {
            if (lores.contains(lore)) {
                for (String replace : replacement) {
                    newLore.add(ThePit.setPlaceholderAPI(p, replace.replaceAll("&", "§")));
                }
                continue;
            }
            newLore.add(ThePit.setPlaceholderAPI(p, lores));
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder replaceStringFromLore(String lorePlaceholder, String replacement) {

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

            replacedLine = ThePit.setPlaceholderAPI(p, replacedLine);
            newLore.add(replacedLine);
        }

        meta.setLore(newLore);
        item.setItemMeta(meta);

        return this;
    }

    public PlaceholdersItemBuilder addLore(List<String> lores) {

        List<String> newLore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();

        if (!lores.isEmpty() && meta.hasLore()) {
            newLore.addAll(meta.getLore());
            for (String lore : lores) {
                newLore.add(ThePit.setPlaceholderAPI(p, lore.replaceAll("&", "§")));
            }
        } else if (!lores.isEmpty()) {
            for (String lore : lores) {
                newLore.add(ThePit.setPlaceholderAPI(p, lore.replaceAll("&", "§")));
            }
        }
        meta.setLore(newLore);

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder setItemFlags(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags(ItemFlag.values());
        meta.addItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder addItemFlags(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder removeItemFlag(ItemFlag... flags) {

        ItemMeta meta = item.getItemMeta();
        meta.removeItemFlags(flags);

        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder setUnbreakable() {

        ItemMeta meta = item.getItemMeta();

        meta.spigot().setUnbreakable(true);
        item.setItemMeta(meta);
        return this;
    }

    public PlaceholdersItemBuilder setIdentifier(String identifier) {

        item = NMSFunction.addNBTTag(item, identifier);
        return this;
    }

    public ItemStack build() {
        return item.clone();
    }
}
