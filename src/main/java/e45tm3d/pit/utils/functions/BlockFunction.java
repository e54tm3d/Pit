package e45tm3d.pit.utils.functions;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockFunction {

    public static void setBlockBreakProgress(Player player, Location loc, int progress) {
        NMSFunction.setBlockBreakProgress(player, loc, progress);
    }

}
