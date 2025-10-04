package e45tm3d.pit.api;

import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Location;

public class Variables {

    public static boolean isSpawnExists() {
        return VariableFunction.isSpawnExists();
    }

    public static void setSpawnPos1(Location loc) {
        VariableFunction.setSpawnPos1(loc);
    }

    public static void setSpawnPos2(Location loc) {
        VariableFunction.setSpawnPos2(loc);
    }

    public static void setSpawnLocation(Location loc) {
        VariableFunction.setSpawnLocation(loc);
    }

    public static boolean inSpawn(Location loc) {
        return VariableFunction.isInSpawn(loc);
    }

    public static String getActiveArena() {
        return VariableFunction.getActiveArena();
    }

    public static double getMaxBuildHeight() {
        return VariableFunction.getMaxBuildHeight();
    }

    public static void setMaxBuildHeight(double height) {
        VariableFunction.setMaxBuildHeight(height);
    }
}
