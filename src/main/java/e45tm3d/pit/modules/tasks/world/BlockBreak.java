package e45tm3d.pit.modules.tasks.world;

import e45tm3d.pit.ThePit;
import e45tm3d.pit.modules.tasks.TaskModule;
import e45tm3d.pit.utils.functions.BlockFunction;
import e45tm3d.pit.utils.maps.BlocksMaps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BlockBreak extends TaskModule {

    @Override
    public void run(TaskModule task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ThePit.getInstance(), () -> {
            long now = System.currentTimeMillis();
            for (Player p : Bukkit.getOnlinePlayers()) {
                for (Location b : BlocksMaps.placed.keySet().stream().toList()) {
                    if (BlocksMaps.placed.containsKey(b)) {
                        long delay = now - BlocksMaps.placed.get(b);
                        if (delay > 1000 && delay <= 2000) {
                            BlockFunction.setBlockBreakProgress(p, b, 1);
                        } else if (delay > 2000 && delay <= 3000) {
                            BlockFunction.setBlockBreakProgress(p, b, 2);
                        } else if (delay > 3000 && delay <= 4000) {
                            BlockFunction.setBlockBreakProgress(p, b, 3);
                        } else if (delay > 4000 && delay <= 5000) {
                            BlockFunction.setBlockBreakProgress(p, b, 4);
                        } else if (delay > 5000 && delay <= 6000) {
                            BlockFunction.setBlockBreakProgress(p, b, 5);
                        } else if (delay > 6000 && delay <= 7000) {
                            BlockFunction.setBlockBreakProgress(p, b, 6);
                        } else if (delay > 7000 && delay <= 8000) {
                            BlockFunction.setBlockBreakProgress(p, b, 7);
                        } else if (delay > 8000 && delay <= 9000) {
                            BlockFunction.setBlockBreakProgress(p, b, 8);
                        } else if (delay > 9000 && delay <= 10000) {
                            BlockFunction.setBlockBreakProgress(p, b, 9);
                        } else if (delay > 10000) {
                            if (!b.getBlock().isEmpty()){
                                BlockFunction.setBlockBreakProgress(p, b, -1);
                                b.getBlock().setType(BlocksMaps.original_block.get(b), true);
                                b.getBlock().setData(BlocksMaps.original_block_data.get(b).getData());
                            }
                            BlocksMaps.placed.remove(b);
                            BlocksMaps.original_block.remove(b);
                        }
                    }
                }
            }
        }, 1, 1);
    }
}