package e45tm3d.pit.utils.maps;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponMaps {

    public static Map<String, Integer> weapon_slots = new HashMap<>();
    public static Map<String, ItemStack> weapon_items = new HashMap<>();

    public static Map<String, Map<Integer, Integer>> weapon_tier_prices = new HashMap<>();
    public static Map<String, Map<Integer, String>> weapon_tier_names = new HashMap<>();
    public static Map<String, Map<Integer, String>> weapon_tier_upgrade_cost_formats = new HashMap<>();
    public static Map<String, Map<Integer, String>> weapon_tier_levelmax_cost_formats = new HashMap<>();
    public static Map<String, Map<Integer, List<String>>> weapon_tier_consume_items = new HashMap<>();
    public static Map<String, Map<Integer, List<String>>> weapon_tier_lores = new HashMap<>();

    public static int getTierPrice(String weapon, int tier) {
        Map<Integer, Integer> setting = weapon_tier_prices.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierPrice(String weapon, int tier, int price) {
        Map<Integer, Integer> setting = weapon_tier_prices.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, price);
    }

    public static String getTierName(String weapon, int tier) {
        Map<Integer, String> setting = weapon_tier_names.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierName(String weapon, int tier, String name) {
        Map<Integer, String> setting = weapon_tier_names.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, name);
    }

    public static String getTierUpgradeCostFormat(String weapon, int tier) {
        Map<Integer, String> setting = weapon_tier_upgrade_cost_formats.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierUpgradeCostFormat(String weapon, int tier, String format) {
        Map<Integer, String> setting = weapon_tier_upgrade_cost_formats.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, format);
    }

    public static String getTierLevelmaxCostFormat(String weapon, int tier) {
        Map<Integer, String> setting = weapon_tier_levelmax_cost_formats.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierLevelmaxCostFormat(String weapon, int tier, String format) {
        Map<Integer, String> setting = weapon_tier_levelmax_cost_formats.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, format);
    }

    public static List<String> getTierLore(String weapon, int tier) {
        Map<Integer, List<String>> setting = weapon_tier_lores.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierLore(String weapon, int tier, List<String> lore) {
        Map<Integer, List<String>> setting = weapon_tier_lores.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, lore);
    }

    public static List<String> getTierConsumeItem(String weapon, int tier) {
        Map<Integer, List<String>> setting = weapon_tier_consume_items.computeIfAbsent(weapon, k -> new HashMap<>());
        return setting.get(tier);
    }

    public static void setTierConsumeItem(String weapon, int tier, List<String> items) {
        Map<Integer, List<String>> setting = weapon_tier_consume_items.computeIfAbsent(weapon, k -> new HashMap<>());
        setting.put(tier, items);
    }
}
