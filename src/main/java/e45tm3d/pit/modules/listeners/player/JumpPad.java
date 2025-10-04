package e45tm3d.pit.modules.listeners.player;

import com.google.common.collect.Maps;
import e45tm3d.pit.api.User;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.functions.VariableFunction;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JumpPad extends ListenerModule {

    private Map<UUID, Long> jump = new HashMap<>();

    @Override
    public void listen(Event event) {
        if (event instanceof PlayerMoveEvent e) {
            Player p = e.getPlayer();
            if (!User.isDevelopMode(p) && VariableFunction.isInSpawn(p.getLocation())) {
                if (hasBlockNearby(p, Material.SLIME_BLOCK)) {

                    Location front = PlayerFunction.getBlockFrontOfEntity(p, 5.0);
                    double deltaX = front.getX() - p.getLocation().getX();
                    double deltaZ = front.getZ() - p.getLocation().getZ();
                    Vector direction = new Vector(deltaX, 1.0, deltaZ);
                    p.setVelocity(direction);

                    if (jump.containsKey(p.getUniqueId())) {
                        if (System.currentTimeMillis() - jump.get(p.getUniqueId()) > 1000) {
                            p.playSound(p.getLocation(), Sound.PISTON_EXTEND, 1, 1);
                            jump.put(p.getUniqueId(), System.currentTimeMillis());
                        }
                    } else {
                        jump.put(p.getUniqueId(), 0L);
                    }
                }
            }
        }
    }

    private static boolean hasBlockNearby(Player player, Material material) {

        boolean b = false;

        Location playerLocation = player.getLocation();
        int playerX = playerLocation.getBlockX();
        int playerY = playerLocation.getBlockY();
        int playerZ = playerLocation.getBlockZ();

        for (int yOffset = -2; yOffset <= -1; yOffset++) {
            for (int xOffset = -3; xOffset <= 3; xOffset++) {
                for (int zOffset = -3; zOffset <= 3; zOffset++) {
                    Location checkLocation = new Location(
                            playerLocation.getWorld(),
                            playerX + xOffset,
                            playerY + yOffset,
                            playerZ + zOffset
                    );

                    if (checkLocation.getBlock().getType() == material) {
                        b = true;
                        break;
                    }
                }
            }
        }
        return b;
    }
}
