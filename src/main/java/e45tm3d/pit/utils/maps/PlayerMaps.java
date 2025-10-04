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

}
