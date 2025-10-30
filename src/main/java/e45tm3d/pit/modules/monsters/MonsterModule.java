package e45tm3d.pit.modules.monsters;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.MonsterSpawnEvent;
import e45tm3d.pit.utils.functions.LocationFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import e45tm3d.pit.utils.lists.MonsterLists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class MonsterModule {

    public abstract boolean isBoss();

    public abstract String getName();

    public abstract String getIdentifier();

    public abstract EntityType getEntityType();

    public abstract void listen(Event event);

    public abstract void run(MonsterModule task);

    public List<Location> getAllMonsterSpawns() {
        return LocationFunction.getAllMonsterSpawns();
    }

    public List<Location> getMonsterSpawns() {
        return LocationFunction.getMonsterSpawns(getIdentifier());
    }

    public boolean isType(Entity entity) {
        return getIdentifier(entity).equalsIgnoreCase(getIdentifier());
    }

    public boolean isMonster(Entity entity) {
        return MonsterLists.entities.contains(entity);
    }

    public String getIdentifier(Entity entity) {
        return entity.hasMetadata("type") ? entity.getMetadata("type").get(0).asString() : "";
    }

    public List<Entity> getAllMonsters() {
        return MonsterLists.entities;
    }

    public List<Entity> getMonsters() {
        List<Entity> entities = new ArrayList<>();
        for (Entity entity : Bukkit.getWorld(VariableFunction.getActiveArena()).getEntities()) {
            if (!Objects.isNull(entity) && !Objects.isNull(entity.getMetadata("type"))) {
                if (entity.hasMetadata("type")) {
                    if (entity.hasMetadata("type")) {
                        List<MetadataValue> metadata = entity.getMetadata("type");
                        if (!metadata.isEmpty() && metadata.get(0).asString().equals(getIdentifier())) {
                            entities.add(entity);
                        }
                    }
                }
            }
        }
        if (entities.isEmpty()) {
            return Collections.emptyList();
        }
        return entities;
    }

    public Entity spawnEntity(Location loc) {

        Entity e = null;

        if (isBoss()) {
            if (MonsterLists.boss.isEmpty()) {

                Entity entity = loc.getWorld().spawnEntity(loc, getEntityType());

                entity.setMetadata("type", new FixedMetadataValue(ThePit.getInstance(), getIdentifier()));

                Bukkit.getPluginManager().callEvent(new MonsterSpawnEvent(entity, getIdentifier(), getName(), loc));

                MonsterLists.entities.add(entity);
                MonsterLists.boss.add(entity);

                e = entity;
            }
        } else {

            Entity entity = loc.getWorld().spawnEntity(loc, getEntityType());

            entity.setMetadata("type", new FixedMetadataValue(ThePit.getInstance(), getIdentifier()));

            Bukkit.getPluginManager().callEvent(new MonsterSpawnEvent(entity, getIdentifier(), getName(), loc));

            MonsterLists.entities.add(entity);

            e = entity;
        }


        return e;
    }

    public void removeAll() {

        List<Entity> toRemove = new ArrayList<>();

        for (Entity entity : Bukkit.getWorld(VariableFunction.getActiveArena()).getEntities()) {
            if (entity.hasMetadata("type")) {
                if (entity.getMetadata("type").get(0).asString().equals(getIdentifier())) {
                    toRemove.add(entity);
                }
            }
        }
        for (Entity remove : toRemove) {
            remove.remove();
        }
    }

    public void register() {
        run(this);
        Monsters.registerMonster(this);
        MonsterLists.entityTypes.add(getIdentifier());
    }

}