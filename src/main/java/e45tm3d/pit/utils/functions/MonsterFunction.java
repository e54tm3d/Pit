package e45tm3d.pit.utils.functions;

import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonsterFunction {

    public static List<Location> getAllMonsterSpawns() {
        return LocationFunction.getAllMonsterSpawns();
    }

    public static List<Location> getMonsterSpawns(String type) {
        return LocationFunction.getMonsterSpawns(type);
    }

    public static boolean isType(Entity entity, String type) {
        return getIdentifier(entity).equalsIgnoreCase(type);
    }

    public static boolean isMonster(Entity entity) {
        return MonsterLists.entities.contains(entity);
    }

    public static String getIdentifier(Entity entity) {
        return entity.hasMetadata("type") ? entity.getMetadata("type").get(0).asString() : "";
    }

    public static List<Entity> getAllMonsters() {
        return MonsterLists.entities;
    }

    public static List<Entity> getMonsters(String type) {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : Bukkit.getWorld(VariableFunction.getActiveArena()).getEntities()) {
            if (!Objects.isNull(entity) && !Objects.isNull(entity.getMetadata("type"))) {
                if (entity.hasMetadata("type")) {
                    if (entity.getMetadata("type").get(0).asString().equals(type)) {
                        entities.add(entity);
                    }
                }
            }
        }
        return entities;
    }
}
