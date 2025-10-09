package e45tm3d.pit.modules.buff;

import com.google.common.collect.Lists;
import e45tm3d.pit.ThePit;
import e45tm3d.pit.api.events.PlayerDeadEvent;
import e45tm3d.pit.api.events.PlayerGainGoldEvent;
import e45tm3d.pit.api.events.PlayerMurderEvent;
import e45tm3d.pit.modules.buff.buffs.*;
import e45tm3d.pit.modules.curse.CurseModule;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.List;

public class Buffs implements Listener {

    private static List<BuffModule> buffs = new ArrayList<>();
    private static boolean registered = false;

    public Buffs() {
        ThePit.getInstance().getLogger().info("Loading buff module...");

        // 取消旧监听器
        if (registered) {
            HandlerList.unregisterAll(this);
            registered = false;
        }

        // 清理旧Buff
        buffs.clear();

        buffs = Lists.newArrayList(new Regeneration(), new ForgeGoldApple(), new Bulldozer(), new StabilizedProjectile(), new WolfTrainer());

        // 注册新监听器
        Bukkit.getPluginManager().registerEvents(this, ThePit.getInstance());
        registered = true;

        List<BuffModule> copy = Lists.newArrayList(buffs);
        copy.forEach(this::register);

        ThePit.getInstance().getLogger().info("Buff module loaded successfully!");
    }

    @EventHandler
    public void onPlayerGainGold(PlayerGainGoldEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void PlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDead(PlayerDeadEvent e) {
        listen(e);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        listen(e);
    }

    @EventHandler
    public void onDrop(InventoryOpenEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onMurder(PlayerMurderEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (e.isCancelled()) {
            return;
        }
        listen(e);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        listen(e);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        listen(e);
    }

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onAnimation(PlayerAnimationEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onShootBow(EntityShootBowEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onRegain(EntityRegainHealthEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.isCancelled() || !e.canBuild()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	@EventHandler
	public void onItemConsume(PlayerItemConsumeEvent e) {
		if (e.isCancelled()) {
			return;
		}
        listen(e);
	}

	private void listen(Event event) {
		for (BuffModule item : buffs) {
            item.listen(event);
		}
	}

    private void register(BuffModule objects) {
        objects.register();
    }

    public static void registerBuff(BuffModule item) {
        if (!buffs.contains(item)) {
            buffs.add(item);
        }
    }
}