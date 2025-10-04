package e45tm3d.pit.utils.functions;

import e45tm3d.pit.utils.maps.EntityMaps;
import org.bukkit.entity.Entity;

public class EntityFunction {

    public static void setType(Entity e, String type) {
        EntityMaps.type.put(e.getUniqueId(), type);
    }

    public static String getType(Entity e) {
        if (!EntityMaps.type.containsKey(e.getUniqueId())) {
            EntityMaps.type.put(e.getUniqueId(), e.getType().toString().toLowerCase());
        }
        return EntityMaps.type.get(e.getUniqueId());
    }
}
