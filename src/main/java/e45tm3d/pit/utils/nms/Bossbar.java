package e45tm3d.pit.utils.nms;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface Bossbar {

    void addPlayer(Player p);

    void removePlayer(Player p);

    void setTitle(String title);

    void setProgress(double progress);

    Location getWitherLocation(Location l);

}
