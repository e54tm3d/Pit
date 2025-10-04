package e45tm3d.pit.utils.maps;

import org.bukkit.configuration.Configuration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuffMaps {
    public static Map<String, ItemStack> buff_menu_items = new HashMap<>();
    public static Map<String, ItemStack> buff_items = new HashMap<>();

    public static Map<String, Integer> price = new HashMap<>();
    public static Map<String, List<String>> consume_items = new HashMap<>();

    public static Map<String, List<String>> unlock = new HashMap<>();
    public static Map<String, List<String>> lock = new HashMap<>();
}
