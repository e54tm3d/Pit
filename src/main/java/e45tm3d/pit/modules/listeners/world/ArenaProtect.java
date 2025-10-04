package e45tm3d.pit.modules.listeners.world;

import e45tm3d.pit.api.enums.Messages;
import e45tm3d.pit.modules.listeners.ListenerModule;
import e45tm3d.pit.utils.functions.PlayerFunction;
import e45tm3d.pit.utils.maps.BlocksMaps;
import org.bukkit.block.BlockState;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ArenaProtect extends ListenerModule {

    @Override
    public void listen(Event event) {
        if (event instanceof BlockBreakEvent e) {
            if (PlayerFunction.isInArena(e.getPlayer())) {
                if (!PlayerFunction.isDevelopMode(e.getPlayer())) {
                    e.setCancelled(true);
                    Messages.BREAK_BLOCK.sendMessage(e.getPlayer()).cooldown(3000);
                }
            }

        } else if (event instanceof BlockPlaceEvent e) {

            BlockState replacedState = e.getBlockReplacedState();

            if (PlayerFunction.isInArena(e.getPlayer())) {
                if (!PlayerFunction.isDevelopMode(e.getPlayer()) && !e.isCancelled()) {
                    Long now = System.currentTimeMillis();
                    BlocksMaps.placed.put(e.getBlock().getLocation(), now);
                    BlocksMaps.original_block.put(e.getBlock().getLocation(), replacedState.getType());
                    BlocksMaps.original_block_data.put(e.getBlock().getLocation(), replacedState.getData());
                }
            }
        }
    }
}
