package e45tm3d.pit.api;

import java.util.UUID;

public class Preplayer {

    String name;
    UUID uuid;

    public Preplayer(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
        return uuid;
    }
}
