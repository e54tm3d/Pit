package e45tm3d.pit.utils.maps;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;

import java.util.HashMap;
import java.util.Map;

public class BlocksMaps {

    public static Map<Location, Long> placed = new HashMap<>();
    public static Map<Location, Material> original_block = new HashMap<>();
    public static Map<Location, MaterialData> original_block_data = new HashMap<>();

}
