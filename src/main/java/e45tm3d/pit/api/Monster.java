package e45tm3d.pit.api;

import e45tm3d.pit.utils.functions.LocationFunction;
import e45tm3d.pit.utils.functions.MonsterFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Monster {

    public List<Location> getAllMonsterSpawns() {
        return MonsterFunction.getAllMonsterSpawns();
    }

    public List<Location> getMonsterSpawns(String type) {
        return MonsterFunction.getMonsterSpawns(type);
    }

    public boolean isType(Entity entity, String type) {
        return MonsterFunction.isType(entity, type);
    }

    public boolean isMonster(Entity entity) {
        return MonsterFunction.isMonster(entity);
    }

    public String getIdentifier(Entity entity) {
        return MonsterFunction.getIdentifier(entity);
    }

    public List<Entity> getAllMonsters() {
        return MonsterFunction.getAllMonsters();
    }

    public List<Entity> getMonsters(String type) {
        return MonsterFunction.getMonsters(type);
    }
}

