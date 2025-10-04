package e45tm3d.pit.utils.functions;

import e45tm3d.pit.api.enums.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationFunction {

    public static List<Location> getMonsterSpawns(String type) {
        List<Location> locations = new ArrayList<>();

        for (String s : Yaml.CONFIG.getConfig().getStringList("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns")) {

            String t = s.split(",")[0];

            if (type.equalsIgnoreCase(t)) {
                double x = Double.parseDouble(s.split(",")[1]);
                double y = Double.parseDouble(s.split(",")[2]);
                double z = Double.parseDouble(s.split(",")[3]);

                Location loc = new Location(Bukkit.getWorld(VariableFunction.getActiveArena()), x, y, z);
                locations.add(loc);
            }
        }
        return locations;
    }

    public static List<Location> getAllMonsterSpawns() {

        List<Location> locations = new ArrayList<>();

        for (String s : Yaml.CONFIG.getConfig().getStringList("worlds.world_manager." + VariableFunction.getActiveArena() + ".monster_spawns")) {


            double x = Double.parseDouble(s.split(",")[1]);
            double y = Double.parseDouble(s.split(",")[2]);
            double z = Double.parseDouble(s.split(",")[3]);

            Location loc = new Location(Bukkit.getWorld(VariableFunction.getActiveArena()), x, y, z);
            locations.add(loc);
        }
        return locations;
    }
}
