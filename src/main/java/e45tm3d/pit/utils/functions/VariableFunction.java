package e45tm3d.pit.utils.functions;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.enums.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.util.Vector;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class VariableFunction {

    public static String getActiveArena() {
        return Yaml.CONFIG.getConfig().getString("worlds.active_arena");
    }

    public static void setActiveArena(String arena) {
        Yaml.CONFIG.getConfig().set("worlds.active_arena", arena);
        ThePit.getInstance().saveConfig();
    }

    public static Location getSpawnLocation() {
        return new Location(
                Bukkit.getWorld(getActiveArena()),
                Yaml.CONFIG.getConfig().getDouble("worlds.world_manager." + getActiveArena() + ".spawn_location.x"),
                Yaml.CONFIG.getConfig().getDouble("worlds.world_manager." + getActiveArena() + ".spawn_location.y"),
                Yaml.CONFIG.getConfig().getDouble("worlds.world_manager." + getActiveArena() + ".spawn_location.z"),
                (float) Yaml.CONFIG.getConfig().getDouble("worlds.world_manager." + getActiveArena() + ".spawn_location.yaw"),
                (float) Yaml.CONFIG.getConfig().getDouble("worlds.world_manager." + getActiveArena() + ".spawn_location.pitch")
        );
    }

    public static void setSpawnLocation(Location spawn) {
        Yaml.CONFIG.getConfig().getStringList("worlds.arenas").add(spawn.getWorld().getName());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + spawn.getWorld().getName() + ".spawn_location.x", spawn.getX());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + spawn.getWorld().getName() + ".spawn_location.y", spawn.getY());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + spawn.getWorld().getName() + ".spawn_location.z", spawn.getZ());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + spawn.getWorld().getName() + ".spawn_location.pitch", spawn.getPitch());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + spawn.getWorld().getName() + ".spawn_location.yaw", spawn.getYaw());
        ThePit.getInstance().saveConfig();
    }

    public static boolean isSpawnExists() {
        boolean exists;
        exists = !Objects.isNull(Yaml.CONFIG.getConfig().getString("worlds.world_manager." + getActiveArena() + ".spawn_location.x"));
        if (Objects.isNull(Yaml.CONFIG.getConfig().getString("worlds.world_manager." + getActiveArena() + ".spawn_location.y"))) exists = false;
        if (Objects.isNull(Yaml.CONFIG.getConfig().getString("worlds.world_manager." + getActiveArena() + ".spawn_location.z"))) exists = false;
        if (Objects.isNull(Yaml.CONFIG.getConfig().getString("worlds.world_manager." + getActiveArena() + ".spawn_location.pitch"))) exists = false;
        if (Objects.isNull(Yaml.CONFIG.getConfig().getString("worlds.world_manager." + getActiveArena() + ".spawn_location.yaw"))) exists = false;
        return exists;
    }

    public static void setSpawnPos1(Location spawn) {
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos1.x", spawn.getX());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos1.y", spawn.getY());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos1.z", spawn.getZ());
        ThePit.getInstance().saveConfig();
    }

    public static void setSpawnPos2(Location spawn) {
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos2.x", spawn.getX());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos2.y", spawn.getY());
        Yaml.CONFIG.getConfig().set("worlds.world_manager." + getActiveArena() + ".spawn_pos2.z", spawn.getZ());
        ThePit.getInstance().saveConfig();
    }

    public static Location getRandomLocation(Location loc, double max_radium, double min_radium) {

        double offsetX = MathFunction.randomDouble(max_radium, min_radium);
        double offsetY = MathFunction.randomDouble(max_radium, min_radium);
        double offsetZ = MathFunction.randomDouble(max_radium, min_radium);

        return new Location(loc.getWorld(), loc.getX() + offsetX, loc.getY() + offsetY, loc.getZ() + offsetZ);
    }

    public static boolean isInSpawn(Location loc) {

        String activeArena = Yaml.CONFIG.getConfig().getString("worlds.active_arena");

        String configPath = "worlds.world_manager." + activeArena + ".spawn_pos";
        Configuration config = Yaml.CONFIG.getConfig();

        double minX = Math.min(config.getDouble(configPath + "1.x"), config.getDouble(configPath + "2.x"));
        double maxX = Math.max(config.getDouble(configPath + "1.x"), config.getDouble(configPath + "2.x"));
        double minY = Math.min(config.getDouble(configPath + "1.y"), config.getDouble(configPath + "2.y"));
        double maxY = Math.max(config.getDouble(configPath + "1.y"), config.getDouble(configPath + "2.y"));
        double minZ = Math.min(config.getDouble(configPath + "1.z"), config.getDouble(configPath + "2.z"));
        double maxZ = Math.max(config.getDouble(configPath + "1.z"), config.getDouble(configPath + "2.z"));

        boolean inX = loc.getX() >= minX && loc.getX() <= maxX;
        boolean inY = loc.getY() >= minY && loc.getY() <= maxY;
        boolean inZ = loc.getZ() >= minZ && loc.getZ() <= maxZ;

        return inX && inY && inZ;
    }

    public static double getMaxBuildHeight() {

        String activeArena = Yaml.CONFIG.getConfig().getString("worlds.active_arena");

        String path = "worlds.world_manager." + activeArena + ".max_build_height";
        Configuration config = Yaml.CONFIG.getConfig();


        return config.getDouble(path);
    }

    public static void setMaxBuildHeight(double y) {

        String activeArena = Yaml.CONFIG.getConfig().getString("worlds.active_arena");

        String path = "worlds.world_manager." + activeArena + ".max_build_height";
        Configuration config = Yaml.CONFIG.getConfig();

        config.set(path, y);
        ThePit.getInstance().saveConfig();
    }
    public static String getFormattedDate() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return currentDate.format(formatter);
    }
    public static String removeBrackets(String input) {
        if (input == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != '[' && c != ']') {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public static Vector toUnitVector(Vector vector) {
        double length = vector.length();
        if (length == 0) {
            return vector.clone();
        }
        return vector.clone().normalize();
    }
}
