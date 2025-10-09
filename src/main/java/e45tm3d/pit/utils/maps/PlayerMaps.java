package e45tm3d.pit.utils.maps;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMaps {

    public static Map<UUID, String> menu = new HashMap<>();
    public static Map<UUID, Long> fight_time = new HashMap<>();

    public static Map<UUID, Integer> level = new HashMap<>();
    public static Map<UUID, Integer> exp = new HashMap<>();
    public static Map<UUID, Integer> kills = new HashMap<>();
    public static Map<UUID, Integer> deaths = new HashMap<>();
    public static Map<UUID, Integer> killstreak = new HashMap<>();
    public static Map<UUID, Integer> helmet_level = new HashMap<>();
    public static Map<UUID, Integer> chestplate_level = new HashMap<>();
    public static Map<UUID, Integer> leggings_level = new HashMap<>();
    public static Map<UUID, Integer> boots_level = new HashMap<>();
    public static Map<UUID, Map<String, Integer>> sword_level = new HashMap<>();

    public static Map<UUID, Map<String, Boolean>> curse = new HashMap<>();
    public static Map<UUID, Map<String, String>> equip_curse = new HashMap<>();
    public static Map<UUID, String> selected_curse = new HashMap<>();

    public static Map<UUID, Map<String, Boolean>> buff = new HashMap<>();
    public static Map<UUID, Map<String, String>> equip_buff = new HashMap<>();
    public static Map<UUID, String> selected_buff = new HashMap<>();

    public static Map<UUID, Map<String, String>> enchance = new HashMap<>();

    public static Map<UUID, String> arena = new HashMap<>();

    public static void clearAllMaps() {
        menu.clear();
        fight_time.clear();
        level.clear();
        exp.clear();
        kills.clear();
        deaths.clear();
        killstreak.clear();
        helmet_level.clear();
        chestplate_level.clear();
        leggings_level.clear();
        boots_level.clear();
        arena.clear();

        // 清理嵌套的Map
        for (Map<String, Integer> map : sword_level.values()) {
            map.clear();
        }
        sword_level.clear();

        for (Map<String, Boolean> map : curse.values()) {
            map.clear();
        }
        curse.clear();

        for (Map<String, String> map : equip_curse.values()) {
            map.clear();
        }
        equip_curse.clear();
        selected_curse.clear();

        for (Map<String, Boolean> map : buff.values()) {
            map.clear();
        }
        buff.clear();

        for (Map<String, String> map : equip_buff.values()) {
            map.clear();
        }
        equip_buff.clear();
        selected_buff.clear();

        for (Map<String, String> map : enchance.values()) {
            map.clear();
        }
        enchance.clear();
    }
}
